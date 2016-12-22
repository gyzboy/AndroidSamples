package com.gyz.javasamples.holding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by guoyizhe on 16/9/6.
 * 邮箱:gyzboy@126.com
 */
public class CompareTest {

    //Comparable 是排序接口。若一个类实现了Comparable接口，就意味着“该类支持排序”。  即然实现Comparable接口的类支持排序

    //Comparator 是比较器接口。我们若需要控制某个类的次序，而该类本身不支持排序(即没有实现Comparable接口)；那么，我们可以建立一个“该类的比较器”来进行排序。
    //这个“比较器”只需要实现Comparator接口即可。也就是说，我们可以通过“实现Comparator类来新建一个比较器”，然后通过该比较器对类进行排序

    public static void main(String[] args) {
        // 新建ArrayList(动态数组)
        ArrayList<Person> list = new ArrayList<Person>();
        // 添加对象到ArrayList中
        list.add(new Person("ccc", 20));
        list.add(new Person("AAA", 30));
        list.add(new Person("bbb", 10));
        list.add(new Person("ddd", 40));

        // 打印list的原始序列
        System.out.printf("Original  sort, list:%s\n", list);

        // 对list进行排序
        // 这里会根据“Person实现的Comparable<String>接口”进行排序，即会根据“name”进行排序
        Collections.sort(list);
        System.out.printf("Name      sort, list:%s\n", list);

        // 通过“比较器(AscAgeComparator)”，对list进行排序
        // AscAgeComparator的排序方式是：根据“age”的升序排序
        Collections.sort(list, new AscAgeComparator());
        System.out.printf("Asc(age)  sort, list:%s\n", list);

        // 通过“比较器(DescAgeComparator)”，对list进行排序
        // DescAgeComparator的排序方式是：根据“age”的降序排序
        Collections.sort(list, new DescAgeComparator());
        System.out.printf("Desc(age) sort, list:%s\n", list);

        // 判断两个person是否相等
        testEquals();
    }

    /**
     * @desc 测试两个Person比较是否相等。
     *   由于Person实现了equals()函数：若两person的age、name都相等，则认为这两个person相等。
     *   所以，这里的p1和p2相等。
     *
     *   TODO：若去掉Person中的equals()函数，则p1不等于p2
     */
    private static void testEquals() {
        Person p1 = new Person("eee", 100);
        Person p2 = new Person("eee", 100);
        if (p1.equals(p2)) {
            System.out.printf("%s EQUAL %s\n", p1, p2);
        } else {
            System.out.printf("%s NOT EQUAL %s\n", p1, p2);
        }
    }

    /**
     * @desc Person类。
     *       Person实现了Comparable接口，这意味着Person本身支持排序
     */
    private static class Person implements Comparable<Person>{
        int age;
        String name;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String toString() {
            return name + " - " +age;
        }

        /**
         * 比较两个Person是否相等：若它们的name和age都相等，则认为它们相等
         */
        boolean equals(Person person) {
            if (this.age == person.age && this.name == person.name)
                return true;
            return false;
        }

        /**
         * @desc 实现 “Comparable<String>” 的接口，即重写compareTo<T t>函数。
         *  这里是通过“person的名字”进行比较的
         */
        @Override
        public int compareTo(Person person) {
            return name.compareTo(person.name);
            //return this.name - person.name;
        }
    }

    /**
     * @desc AscAgeComparator比较器
     *       它是“Person的age的升序比较器”
     */
    private static class AscAgeComparator implements Comparator<Person> {

        @Override
        public int compare(Person p1, Person p2) {
            return p1.getAge() - p2.getAge();
        }
    }

    /**
     * @desc DescAgeComparator比较器
     *       它是“Person的age的升序比较器”
     */
    private static class DescAgeComparator implements Comparator<Person> {

        @Override
        public int compare(Person p1, Person p2) {
            return p2.getAge() - p1.getAge();
        }
    }

}
