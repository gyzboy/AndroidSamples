package com.gyz.javasamples.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoyizhe on 16/9/5.
 * 邮箱:gyzboy@126.com
 */
public class WildcardTest {
    public static void main(String[] args) {
        String wildCard = "泛型类: class Tool<Q>{}\n" +
                "泛型方法: public <W> void method(W w)\n" +
                "静态泛型方法:public static <Q> void function(Q t)\n" +
                "泛型接口: interface Inter<T>{}\n" +
                " class InterImpl<R> implements Inter<T>{}\n" +
                "使用通配符?开启了编译时检测,类型错误时编译不通过";
        System.out.println(wildCard);


        ArrayList<Integer> k = new ArrayList<>();
        ArrayList<String > v = new ArrayList<>();
        k.add(1);
        k.add(2);
        k.add(3);
        v.add("aaa");
        v.add("bbb");
        v.add("ccc");
        System.out.println(toMap(k,v));

    }

    public static <K,V> Map<K,V> toMap(List<K> key,List<V> value){
        Map<K,V> map = new HashMap<>();
        for (int i = 0; i < key.size(); i++) {
            map.put(key.get(i),value.get(i));
        }
        return map;
    }
}


interface IWildCard<Request,Response>{
    Response process(Request request);
}
class WildCard implements IWildCard<String,Integer>{

    @Override
    public Integer process(String s) {
        return Integer.valueOf(s);
    }
}

/*
class TT<T>{

    private T t1 = new T();//失败,在初始化时需要知道确切的类型

    public TT() {
        System.out.println("wildcard");
    }


    public void handle(List<String> s){//编译不通过,因为在编译时会类型擦除
        System.out.println(s);
    }
    public void handle(List<Integer> i){
        System.out.println(i);
    }


// 读取的元素使用了Object类型来表示，这是安全的，因为所有的类都是Object的子类。
// 这里就又出现了另外一个问题，如下代码所示，如果试图往使用通配符？的集合中加入对象，就会在编译时出现错误。
// 需要注意的是，这里不管加入什么类型的对象都会出错。这是因为通配符？表示该集合存储的元素类型未知，可以是任何类型。
// 往集合中加入元素需要是一个未知元素类型的子类型，正因为该集合存储的元素类型未知，所以我们没法向该集合中添加任何元素。
// 唯一的例外是null，因为null是所有类型的子类型，所以尽管元素类型不知道，但是null一定是它的子类型。
// 另一方面，我们可以从List lists中获取对象，虽然不知道List中存储的是什么类型，但是可以肯定的是存储的类型一定是Object的子类型，
// 所以可以用Object类型来获取值。
    public void outPut(List<?> list){
        for (Object o : list) {
            System.out.println(o.toString());
        }
    }
    public void add(List<?> list,String str){
//        list.add(str);
        list.add(null);
    }

}
*/
