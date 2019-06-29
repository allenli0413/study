package com.liyh.complier;

import com.google.auto.service.AutoService;
import com.liyh.annotation.NeedsPermission;
import com.liyh.annotation.OnNeverAskAgain;
import com.liyh.annotation.OnPermissionDenied;
import com.liyh.annotation.OnShowRationale;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)//加上这个注解才能去执行
public class PermissionProcessor extends AbstractProcessor {
    private Messager mMessager; //用来报告错误，警告，提示
    private Elements elementUtils;  //Elements中包含用于操作的工具
    private Filer filer;        //用来创建新的源文件，class以及其他文件
    private Types typeUtils; //包含用于操作TypeMirror的工具方法

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //此方法主要是去初始化需要用到的工具
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
        typeUtils = processingEnvironment.getTypeUtils();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //添加支持的注解类型
        Set<String> setTypes = new LinkedHashSet<>();
        setTypes.add(NeedsPermission.class.getCanonicalName());
        setTypes.add(OnNeverAskAgain.class.getCanonicalName());
        setTypes.add(OnPermissionDenied.class.getCanonicalName());
        setTypes.add(OnShowRationale.class.getCanonicalName());
        return setTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //用哪个版本的jdk编译
        return SourceVersion.RELEASE_8;
    }

    final String CLASS_SUFFIX = "$Permissions";

    /**
     * 注解处理器的核心方法，处理具体的注解，生成新的 Java Class文件
     * 强调的是，一行一行代码的去写
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //获取所有拥有NeedsPermission注解的方法元素
        Set<? extends Element> needsPermissionSet = roundEnvironment.getElementsAnnotatedWith(NeedsPermission.class);
        // 保存键值对，key是com.netease.permission.MainActivity   value是所有带NeedsPermission注解的方法集合
        Map<String, List<ExecutableElement>> needsPermissionMap = new HashMap<>();
        //遍历所有带有注解的方法元素
        for (Element needsPermissionElement : needsPermissionSet) {
            //转换成原始的属性元素
            ExecutableElement executableElement = (ExecutableElement) needsPermissionElement;
            //通过属性元素获取全类名
            String activityName = getActivityName(executableElement);
            //通过全类名获取缓存中带有此注解的方法集合
            List<ExecutableElement> list = needsPermissionMap.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                //加入map集合中，引用变量executableElements，可以动态改值
                needsPermissionMap.put(activityName, list);
            }
            list.add(executableElement);
            // 测试打印：每个方法的名字
            System.out.println("NeedsPermission executableElement >>> " + executableElement.getSimpleName().toString());

        }

        // 获取MainActivity中所有带OnNeverAskAgain注解的方法
        Set<? extends Element> onNeverAskAgainSet = roundEnvironment.getElementsAnnotatedWith(OnNeverAskAgain.class);
        Map<String, List<ExecutableElement>> onNeverAskAgainMap = new HashMap<>();
        for (Element element : onNeverAskAgainSet) {
            ExecutableElement executableElement = (ExecutableElement) element;
            String activityName = getActivityName(executableElement);
            List<ExecutableElement> list = onNeverAskAgainMap.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                onNeverAskAgainMap.put(activityName, list);
            }
            list.add(executableElement);
            System.out.println("executableElement >>> " + executableElement.getSimpleName().toString());
        }

        // 获取MainActivity中所有带OnPermissionDenied注解的方法
        Set<? extends Element> onPermissionDeniedSet = roundEnvironment.getElementsAnnotatedWith(OnPermissionDenied.class);
        Map<String, List<ExecutableElement>> onPermissionDeniedMap = new HashMap<>();
        for (Element element : onPermissionDeniedSet) {
            ExecutableElement executableElement = (ExecutableElement) element;
            String activityName = getActivityName(executableElement);
            List<ExecutableElement> list = onPermissionDeniedMap.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                onPermissionDeniedMap.put(activityName, list);
            }
            list.add(executableElement);
            System.out.println("executableElement >>> " + executableElement.getSimpleName().toString());
        }

        // 获取MainActivity中所有带OnShowRationale注解的方法
        Set<? extends Element> onShowRationaleMapSet = roundEnvironment.getElementsAnnotatedWith(OnShowRationale.class);
        Map<String, List<ExecutableElement>> onShowRationaleMap = new HashMap<>();
        for (Element element : onShowRationaleMapSet) {
            ExecutableElement executableElement = (ExecutableElement) element;
            String activityName = getActivityName(executableElement);
            List<ExecutableElement> list = onShowRationaleMap.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                onShowRationaleMap.put(activityName, list);
            }
            list.add(executableElement);
            System.out.println("executableElement >>> " + executableElement.getSimpleName().toString());
        }

        //----------------------------------造币过程------------------------------------
        // 获取Activity完整的字符串类名（包名 + 类名）
        for (String activityName : needsPermissionMap.keySet()) {
            // 获取"com.netease.permission.MainActivity"中所有控件方法的集合
            List<ExecutableElement> needsPermissionElements = needsPermissionMap.get(activityName);
            List<ExecutableElement> onNeverAskAgainElements = onNeverAskAgainMap.get(activityName);
            List<ExecutableElement> onPermissionDeniedElements = onPermissionDeniedMap.get(activityName);
            List<ExecutableElement> onShowRationaleElements = onShowRationaleMap.get(activityName);

            try {
                //创建一个新的源文件（Class），并返回一个对象，允许写入它
                JavaFileObject sourceFile = filer.createSourceFile(activityName + CLASS_SUFFIX);
                // 通过方法标签获取包名标签（任意一个属性标签的父节点都是同一个包名）
                String packageName = getPackageName(needsPermissionElements.get(0));
                // 类名：MainActivity$Permissions，不是com.netease.permission.MainActivity$Permissions
                // 通过属性元素获取它所属的MainActivity类名，再拼接后结果为：MainActivity$Permissions
                String activitySimpleName = getActivitySimpleName(needsPermissionElements.get(0)) + CLASS_SUFFIX;
                // 定义Writer对象，开启造币过程
                Writer writer = sourceFile.openWriter();
                System.out.println("activityName >>> " + activityName + "\nactivitySimpleName >>> " + activitySimpleName);

                System.out.println("开始造币 ----------------------------------->");
                // 生成包
                writer.write("package " + packageName + ";\n");
                // 生成要导入的接口类（必须手动导入）
                writer.write("import com.liyh.permission.listener.RequestPermission;\n");
                writer.write("import com.liyh.permission.listener.PermissionRequest;\n");
                writer.write("import com.liyh.permission.utils.PermissionUtils;\n");
                writer.write("import android.support.v7.app.AppCompatActivity;\n");
                writer.write("import android.support.v4.app.ActivityCompat;\n");
                writer.write("import android.support.annotation.NonNull;\n");
                writer.write("import java.lang.ref.WeakReference;\n");
                writer.write("\n");
                // 生成类
                writer.write("public class " + activitySimpleName +
                        " implements RequestPermission<" + activityName + "> {\n");

                // 生成常量属性
                writer.write("      private static final int REQUEST_SHOWCAMERA = 666;\n");
                writer.write("      private static String[] PERMISSION_SHOWCAMERA;\n");

                // 生成requestPermission方法
                writer.write("      public void requestPermission(" + activityName + " target, String[] permissions) {\n");

                writer.write("          PERMISSION_SHOWCAMERA = permissions;\n");
                writer.write("          if (PermissionUtils.hasSelfPermissions(target, PERMISSION_SHOWCAMERA)) {\n");

                // 循环生成MainActivity每个权限申请方法
                for (ExecutableElement executableElement : needsPermissionElements) {
                    // 获取方法名
                    String methodName = executableElement.getSimpleName().toString();
                    // 调用申请权限方法
                    writer.write("          target." + methodName + "();\n");
                }

                writer.write("          } else if (PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_SHOWCAMERA)) {\n");

                // 循环生成MainActivity每个提示用户为何要开启权限方法
                if (onShowRationaleElements != null && !onShowRationaleElements.isEmpty()) {
                    for (ExecutableElement executableElement : onShowRationaleElements) {
                        // 获取方法名
                        String methodName = executableElement.getSimpleName().toString();
                        // 调用提示用户为何要开启权限方法
                        writer.write("      target." + methodName + "(new PermissionRequestImpl(target));\n");
                    }
                }

                writer.write("          } else {\n");
                writer.write("              ActivityCompat.requestPermissions(target, PERMISSION_SHOWCAMERA, REQUEST_SHOWCAMERA);\n}\n}\n");

                // 生成onRequestPermissionsResult方法
                writer.write("      public void onRequestPermissionsResult(" + activityName + " target, int requestCode, @NonNull int[] grantResults) {");
                writer.write("          switch(requestCode) {\n");
                writer.write("              case REQUEST_SHOWCAMERA:\n");
                writer.write("                  if (PermissionUtils.verifyPermissions(grantResults)) {\n");

                // 循环生成MainActivity每个权限申请方法
                for (ExecutableElement executableElement : needsPermissionElements) {
                    // 获取方法名
                    String methodName = executableElement.getSimpleName().toString();
                    // 调用申请权限方法
                    writer.write("                      target." + methodName + "();\n");
                }

                writer.write("                  } else if (!PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION_SHOWCAMERA)) {\n");

                // 循环生成MainActivity每个不再询问后的提示
                if (onNeverAskAgainElements != null && !onNeverAskAgainElements.isEmpty()) {
                    for (ExecutableElement executableElement : onNeverAskAgainElements) {
                        // 获取方法名
                        String methodName = executableElement.getSimpleName().toString();
                        // 调用不再询问后的提示
                        writer.write("              target." + methodName + "();\n");
                    }
                }

                writer.write("                  } else {\n");

                // 循环生成MainActivity每个拒绝时的提示方法
                if (onPermissionDeniedElements != null && !onPermissionDeniedElements.isEmpty()) {
                    for (ExecutableElement executableElement : onPermissionDeniedElements) {
                        // 获取方法名
                        String methodName = executableElement.getSimpleName().toString();
                        // 调用拒绝时的提示方法
                        writer.write("              target." + methodName + "();\n");
                    }
                }

                writer.write("                  }\nbreak;\ndefault:\nbreak;\n}\n}\n");

                // 生成接口实现类：PermissionRequestImpl implements PermissionRequest
                writer.write("private static final class PermissionRequestImpl implements PermissionRequest {\n");
                writer.write("private final WeakReference<" + activityName + "> weakTarget;\n");
                writer.write("private PermissionRequestImpl(" + activityName + " target) {\n");
                writer.write("this.weakTarget = new WeakReference(target);\n}\n");
                writer.write("public void proceed() {\n");
                writer.write(activityName + " target = (" + activityName + ")this.weakTarget.get();\n");
                writer.write("if (target != null) {\n");
                writer.write("ActivityCompat.requestPermissions(target, PERMISSION_SHOWCAMERA, REQUEST_SHOWCAMERA);\n}\n}\n}\n");

                // 最后结束标签，造币完成
                writer.write("\n}");
                System.out.println("结束 ----------------------------------->");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private String getActivitySimpleName(ExecutableElement executableElement) {
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
        return typeElement.getSimpleName().toString();
    }

    private String getActivityName(ExecutableElement executableElement) {
        //通过方法元素获取上衣结构体的元素，即类元素
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
        String packageName = getPackageName(typeElement);
        //获取类元素的名字
        String activitySimpleName = typeElement.getSimpleName().toString();
        String activityFullName = packageName + "." + activitySimpleName;
        System.out.println("fullClassName = " + activityFullName);
        return activityFullName;
    }

    //通过类元素获取包元素
    private String getPackageName(TypeElement typeElement) {
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        System.out.println("packageName = " + packageName);
        return packageName;
    }

    //通过方法元素获取包元素
    private String getPackageName(ExecutableElement executableElement) {
        //通过方法元素获取上衣结构体的元素，即类元素
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
        String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
        System.out.println("packageName = " + packageName);
        return packageName;
    }

    private String getSimpleName(ExecutableElement executableElement) {
        //通过方法元素获取上衣结构体的元素，即类元素
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
        //获取类元素的名字
        String activitySimpleName = typeElement.getSimpleName().toString();
        return activitySimpleName;
    }
}
