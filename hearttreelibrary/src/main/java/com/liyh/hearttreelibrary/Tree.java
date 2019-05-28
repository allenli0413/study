package com.liyh.hearttreelibrary;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Tree {


    private enum Step {
        BRANCHES_GROWING,//绘制树干
        BLOOMS_GROWING,//绘制花瓣
        MOVING_SNAPSHOT,//移动整体
        BLOOM_FALLING//花瓣凋落
    }

    private Step step = Step.BRANCHES_GROWING;
    //绘制树干Branch
    private float branchesDx;
    private float branchesDy;
    private LinkedList<Branch> growingBranches = new LinkedList<>();

    // scale factor
    private static final float CROWN_RADIUS_FACTOR = 0.35f;
    private static final float STAND_FACTOR = (CROWN_RADIUS_FACTOR / 0.28f);
    private static final float BRANCHES_FACTOR = 1.3f * STAND_FACTOR;
    private final float resolutionFactor;//屏幕像素缩放因子

    //Snapshot
    private final Snapshot treeSnapshot;
    private Paint snapshotPaint = new Paint();

    // Offset
    private float snapshotDx;
    private float xOffset;
    private float maxXOffset;

    // Bloom params
    private static final int BLOOM_NUM = 320;
    private static final int BLOOMING_NUM = BLOOM_NUM / 8;

    // Falling blooms
    private float fMaxY;
    private List<FallingBloom> fallingBlooms = new ArrayList<>();

    // Crown of a tree
    private float bloomsDx;
    private float bloomsDy;
    private LinkedList<Bloom> growingBlooms = new LinkedList<>();
    private LinkedList<Bloom> cacheBlooms = new LinkedList<>();

    public Tree(int canvasWidth, int canvasHeight) {
        //数据初始化
        resolutionFactor = canvasHeight / 1080f;
        TreeMaker.init(canvasHeight, CROWN_RADIUS_FACTOR);
        Bloom.initDisplayParam(resolutionFactor);
        //Snapshot
        float snapshotWidth = 816f * STAND_FACTOR * resolutionFactor;
        treeSnapshot = new Snapshot(Bitmap.createBitmap(Math.round(snapshotWidth),
                canvasHeight, Bitmap.Config.ARGB_8888));
        // Branches
        float branchesWidth = 375f * BRANCHES_FACTOR * resolutionFactor;
        float branchesHeight = 490f * BRANCHES_FACTOR * resolutionFactor;
        branchesDx = (snapshotWidth - branchesWidth) / 2f - 40f * STAND_FACTOR;
        branchesDy = canvasHeight - branchesHeight;
        growingBranches.add(TreeMaker.getBranches());

        // Blooms
        bloomsDx = snapshotWidth / 2f;
        bloomsDy = 435f * STAND_FACTOR * resolutionFactor;
        TreeMaker.fillBlooms(cacheBlooms, BLOOM_NUM);

        // Moving snapshot
        maxXOffset = (canvasWidth - snapshotWidth) / 2f - 40f;

        // Falling blooms
        fMaxY = canvasHeight - bloomsDy;
        TreeMaker.fillFallingBlooms(fallingBlooms, 3);

        snapshotDx = (canvasWidth - snapshotWidth) / 2f;
    }

    public void draw(Canvas canvas) {
        Log.e("THINK", "TREE DRAW");
        canvas.drawColor(0xFFFFFFEE);
        canvas.save();
        canvas.translate(snapshotDx + xOffset, 0);
        switch (step) {
            case BRANCHES_GROWING:
                drawBranches();
                drawSnapshot(canvas);
                break;
            case BLOOMS_GROWING:
                drawBlooms();
                drawSnapshot(canvas);
                break;
            case MOVING_SNAPSHOT:
                movingSnapshot();
                drawSnapshot(canvas);
                break;
            case BLOOM_FALLING:
                drawSnapshot(canvas);
                drawFallingBlooms(canvas);

                break;
            default:
                break;
        }
        canvas.restore();
    }


    private void drawSnapshot(Canvas canvas) {
        canvas.drawBitmap(treeSnapshot.bitmap, 0, 0, snapshotPaint);
    }

    private void drawBranches() {

        if (!growingBranches.isEmpty()) {
            LinkedList<Branch> tempBranches = null;
            treeSnapshot.canvas.save();
            treeSnapshot.canvas.translate(branchesDx, branchesDy);
            Iterator<Branch> iterator = growingBranches.iterator();
            while (iterator.hasNext()) {
                Branch branch = iterator.next();
                if (!branch.grow(treeSnapshot.canvas, BRANCHES_FACTOR * resolutionFactor)) {
                    iterator.remove();
                    if (branch.childList != null) {
                        if (tempBranches == null) {
                            tempBranches = branch.childList;
                        } else {
                            tempBranches.addAll(branch.childList);
                        }
                    }
                }
            }
            treeSnapshot.canvas.restore();
            if (tempBranches != null) {
                growingBranches.addAll(tempBranches);
            }
        }
        if (growingBranches.isEmpty()) {
            //绘制树干完成
            step = Step.BLOOMS_GROWING;
        }
    }

    private void drawBlooms() {

        while (growingBlooms.size()<BLOOMING_NUM&& !cacheBlooms.isEmpty()){
            growingBlooms.add(cacheBlooms.pop());
        }
        Iterator<Bloom> iterator=growingBlooms.iterator();
        treeSnapshot.canvas.save();
        treeSnapshot.canvas.translate(bloomsDx,bloomsDy);
        while (iterator.hasNext()){
            Bloom bloom=iterator.next();
            if(!bloom.grow(treeSnapshot.canvas)){
                iterator.remove();
            }
        }
        treeSnapshot.canvas.restore();
        if(growingBlooms.isEmpty()&&cacheBlooms.isEmpty()){
            //绘制树冠完成
            step=Step.MOVING_SNAPSHOT;
        }
    }

    private void movingSnapshot() {
        Log.e("THINK movingSnapshot",xOffset+"  "+maxXOffset);
        if (xOffset > maxXOffset) {
            //整体移动完成
            step = Step.BLOOM_FALLING;
        } else {
            xOffset += 4f;
        }
    }

    private void drawFallingBlooms(Canvas canvas) {
        Iterator<FallingBloom> iterator=fallingBlooms.iterator();
        canvas.save();
        canvas.translate(bloomsDx,bloomsDy);
        while (iterator.hasNext()){
            FallingBloom bloom=iterator.next();
            if(!bloom.fall(canvas,fMaxY)){
                iterator.remove();
                TreeMaker.recycleBloom(bloom);
            }
        }
        canvas.restore();
        if(fallingBlooms.size()<3){
            TreeMaker.fillFallingBlooms(fallingBlooms,CommonUtil.random(1,2));
        }
    }

}
