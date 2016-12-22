package com.gyz.javasamples.initialization;

/**
 * Created by guoyizhe on 16/8/30.
 * 邮箱:gyzboy@126.com
 */
public class CleanTest {

    boolean isChecked = true;

    CleanTest(boolean b){
        isChecked = b;
    }

    void checked(){
        isChecked = true;
    }

    @Override
    protected void finalize() throws Throwable {
        if (!isChecked) {
            System.out.println("not checked");
        }
        super.finalize();
    }

    public static void main(String[] args){
        String clean = "finalize用法:\n" +
                "   在调用C方法时可以使用finalize释放资源,比如JNI\n" +
                "   最后缺陷验证终结条件";

        System.out.println(clean);




        CleanTest ct = new CleanTest(false);
        ct.checked();

        new CleanTest(false);//有错误情况,gc时会调用finalize达到了缺陷验证效果
        System.gc();
    }
}
