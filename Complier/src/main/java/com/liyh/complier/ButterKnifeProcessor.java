package com.liyh.complier;

import com.google.auto.service.AutoService;
import com.liyh.annotation.BindView;
import com.liyh.annotation.OnClick;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)//加上这个注解才能去执行
public class ButterKnifeProcessor extends AbstractProcessor {
    private Messager mMessager;
    private Elements elementUtils;  //Elements中包含用于操作的工具
    private Filer filer;        //用来创建新的源文件，class以及其他文件

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        //此方法主要是去初始化需要用到的工具
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //添加支持的注解类型
        Set<String> setTypes = new LinkedHashSet<>();
        setTypes.add(BindView.class.getCanonicalName());
        setTypes.add(OnClick.class.getCanonicalName());
        return setTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        //用哪个版本的jdk编译
        return SourceVersion.RELEASE_8;
    }

    /**
     * 注解处理器的核心方法，处理具体的注解，生成新的 Java Class文件
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        Set<? extends Element> rootElements = roundEnvironment.getRootElements();
//        for (Element rootElement : rootElements) {
//            PackageElement packageOf = elementUtils.getPackageOf(rootElement);
//            System.out.println(rootElement.getSimpleName());
//            System.out.println(packageOf.getQualifiedName());
//        }
        mMessager.printMessage(Diagnostic.Kind.NOTE, "processing...");
        try {
            JavaFileObject fileObject = filer.createSourceFile("LauncherActivity$ViewBinder");
            //获取writer，wirter就类似一支笔，去写java文件里的每一行代码
            Writer writer = fileObject.openWriter();
            writer.write("package com.liyh.app;\n");
            writer.write("\n");
            writer.write("import com.liyh.butterknifelibrary.DebouncingOnClickListener;\n");
            writer.write("import com.liyh.butterknifelibrary.ViewBinder;\n");
            writer.write("\n");
            writer.write("import com.liyh.app.LauncherActivity;\n");
            writer.write("import com.liyh.app.R;\n");
            writer.write("\n");
            writer.write("/**\n");

            writer.write(" * @author Yahri Lee\n");

            writer.write(" * @date 2019 年 05 月 27 日\n");

            writer.write(" * @time 01 时 55 分\n");

            writer.write(" * @descrip :\n");

            writer.write(" */\n");

            writer.write("public class LauncherActivity$ViewBinder implements ViewBinder<LauncherActivity> {\n");

            writer.write("    @Override\n");

            writer.write("    public void bind(LauncherActivity target) {\n");

            writer.write("        target.tvText = target.findViewById(R.id.tv_text);\n");

            writer.write("        target.btJump = target.findViewById(R.id.bt_jump);\n");

            writer.write("        target.btJump.setOnClickListener(new DebouncingOnClickListener() {\n");

            writer.write("            @Override\n");

            writer.write("            protected void doClick() {\n");

            writer.write("                target.onViewClicked(target.btJump);\n");

            writer.write("            }\n");

            writer.write("        });\n");
            writer.write("    }\n");
            writer.write("}");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
