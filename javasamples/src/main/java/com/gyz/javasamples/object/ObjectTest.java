package com.gyz.javasamples.object;

/**
 * Created by guoyizhe on 16/8/29.
 * 邮箱:gyzboy@126.com
 */
public class ObjectTest {

    public static void main(String[] args){
        String accessTag = "Java四种访问指定词:\n" +
                "  public 对任何人可用\n" +
                "  private 只有类型内部可用\n" +
                "  protected 跟private类似,不过继承类可用\n" +
                "  default 默认修饰符,包内访问权限";

        System.out.println(accessTag);


        String inherit = "继承:\n" +
                "  基类改变时所有子类都会相应改变\n" +
                "  继承现有类型也就创造了新的类型,新的类型包括现在所有类型成员(虽然private成员无法访问),同时复制了基类的接口,所以基类与子类具有相同的类型\n" +
                "  子类可以调用基类方法\n" +
                "  不可以重载域和静态方法,只可以重载方法";

        System.out.println(inherit);

        String polymorphic = "多态:\n" +
                "  基类调用子类方法\n" +
                "  基于动态绑定,在代码运行时决定使用哪种对象\n" +
                "  不可以覆盖私有方法\n" +
                "  不可以重载域和静态方法,只可以重载方法";
        System.out.println(polymorphic);

        String objectSave = "对象存储:\n" +
                "  在程序中,Main函数就是栈的起始点,也是程序的起始点,程序运行永远在栈中进行,所以参数传递时,只传递基本类型和对象引用,而不是对象本身\n" +
                "  1.寄存器:最快的存储区,系统控制\n" +
                "  2.栈:RAM中,存储Java引用,局部变量,参数\n" +
                "  3.堆:RAM中,存储Java对象,每一个实体都有内存首地址,都有默认值\n" +
                "  4.常量区(方法区):程序代码内部,置于特殊静态存储区,同时也有字符串存放\n" +
                "  5.非RAM,流和持久化对象\n" +
                "  特殊:基本类型不是new出来的,而是一个并非是引用的'自动'变量,这个变量直接存储'值',并置于栈中,因此更加高效";
        System.out.println(objectSave);

        String baseCategory = "基本类型:\n" +
                " 类型     |  大小  |  包装器类型  | 默认值\n" +
                " boolean  |    -    |  Booelan   |  false\n" +
                " char     |  2Byte  |  Charactor |  null  无符号\n" +
                " byte     |  1Byte  |  Byte      |  (byte)0\n"  +
                " short    |  2Byte  |  Short     |  (short)0\n" +
                " int      |  4Byte  |  Integer   |  0\n" +
                " long     |  8Byte  |  Long      |  0L\n" +
                " float    |  4Byte  |  Float     |  0.0f\n" +
                " double   |  8Byte  |  Double    |  0.0d\n"+
                " void     |    -    |  Void";

        System.out.println(baseCategory);
    }
}
