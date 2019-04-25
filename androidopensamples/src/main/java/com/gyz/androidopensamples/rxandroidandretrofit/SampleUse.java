package com.gyz.androidopensamples.rxandroidandretrofit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gyz.androidopensamples.rxandroidandretrofit.SampleUseBean.Person;
import com.gyz.androidopensamples.rxandroidandretrofit.SampleUseBean.Plan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;

/**
 * author:guoyizhe
 * date:19-4-25
 * email:guoyizhe@xiaomi.com
 */

public class SampleUse extends Activity {
    private static final String TAG = SampleUse.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Func1 指有一个参数,有返回值的方法
        //Action1 指有一个参数的方法,没有返回值

    }

    private void useCreate() {
        //Observable 被观察者
        //Observer   观察者
        //subscribe  订阅   订阅后可以让观察者观察被观察者的动作改变
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "=========================currentThread name: " + Thread.currentThread().getName());
                e.onNext(1);//发送该事件,观察者会回调onNext方法
                e.onNext(2);
                e.onNext(3);
                e.onComplete();//发送该事件,观察者会回调onComplete方法,当发送该事件后,观察者不再接受其他事件
                //e.onError    //发送该事件,观察者会回调onError方法,当发送该事件后,观察者不再接受其他事件
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "======================onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "======================onNext " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "======================onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "======================onComplete");
            }
        });
    }

    private void useJust() {
        //直接创建被观察者并发送事件,最多支持10个事件
        Observable.just(1, 2, 3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "=================onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "=================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "=================onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "=================onComplete ");
                    }
                });

//        =================onSubscribe
//        =================onNext 1
//        =================onNext 2
//        =================onNext 3
//        =================onComplete

    }

    private void useFromArray() {
        //类似Just,但没有了10个事件的数量限制,并且可以传入数组
        Integer array[] = {1, 2, 3, 4};
        Observable.fromArray(array)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "=================onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "=================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "=================onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "=================onComplete ");
                    }
                });
//        =================onSubscribe
//        =================onNext 1
//        =================onNext 2
//        =================onNext 3
//        =================onNext 4
//        =================onComplete


    }

    private void useFromCallable() {
        //callable带有返回值
        Observable.fromCallable(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                return 1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "================accept " + integer);
            }
        });
//      ================accept 1
    }

    private void useFromFuture() {
        //增加了callable的返回值
        //可以使用cancel操作
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Log.d(TAG, "CallableDemo is Running");
                return "返回结果";
            }
        });

        Observable.fromFuture(futureTask)
                .doOnSubscribe(new Consumer<Disposable>() {//只有在订阅时才会发送事件
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        futureTask.run();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "================accept " + s);
                    }
                });

    }

    private void useFromIterable() {
        //直接发送List给被观察者
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        Observable.fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "=================onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "=================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "=================onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "=================onComplete ");
                    }
                });
//        =================onSubscribe
//        =================onNext 0
//        =================onNext 1
//        =================onNext 2
//        =================onNext 3
//        =================onComplete

    }

    int i = 100;

    private void useDefer() {
        //defer是当观察者订阅被观察者的时候才去创建被观察者
        //所以每订阅一次就会打印一次，并且都是打印 i 最新的值。

        // i 要定义为成员变量
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

        i = 200;

        Observer observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "================onNext " + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);

        i = 300;

        observable.subscribe(observer);

        //================onNext 200
        //================onNext 300
    }

    private void useTimer() {
        //到达时间后发送0到观察者
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "===============onNext " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void useInterval() {
        //每隔一段时间就+1,从0开始
        //IntervalRange 类似 指定开始值和数量
        //range指定开始值和数量
        //rangeLong 类似于range
        Observable.interval(0, 4, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "==============onSubscribe ");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "==============onNext " + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

//         ==============onSubscribe
//         ==============onNext 0
//         ==============onNext 1
//         ==============onNext 2
//         ==============onNext 3
//         ==============onNext 4
//         ==============onNext 5

    }

    private void useENE() {
//        empty()：直接发送 onComplete() 事件
//        never()：不发送任何事件
//        error()：发送 onError() 事件
    }

    private void useMap() {
        //Map可以让观察者将被观察者的类型转换成其他类型
        Observable.just(1, 2, 3)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "I'm " + integer;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "===================onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "===================onNext " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
//                ===================onSubscribe
//                ===================onNext I'm 1
//                ===================onNext I'm 2
//                ===================onNext I'm 3
    }

    private void useFlatMap() {
        //类似Map,但返回的是一个Obserable对象,发送事件的顺序是无序的
        //concatMap与其类似,
        List<Person> personList = new ArrayList<>();
        Observable.fromIterable(personList)
                .flatMap(new Function<Person, ObservableSource<Plan>>() {
                    @Override
                    public ObservableSource<Plan> apply(Person person) {
                        return Observable.fromIterable(person.getPlanList());
                    }
                })
                .flatMap(new Function<Plan, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Plan plan) throws Exception {
                        return Observable.fromIterable(plan.getActionList());
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "==================action: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void useBuffer() {
        //从需要发送的事件当中获取一定数量的事件，并将这些事件放到缓冲区当中一并发出
        Observable.just(1, 2, 3, 4, 5)
                .buffer(2, 1)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d(TAG, "================缓冲区大小： " + integers.size());
                        for (Integer i : integers) {
                            Log.d(TAG, "================元素： " + i);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
//                ================缓冲区大小： 2
//                ================元素： 1
//                ================元素： 2
//                ================缓冲区大小： 2
//                ================元素： 2
//                ================元素： 3
//                ================缓冲区大小： 2
//                ================元素： 3
//                ================元素： 4
//                ================缓冲区大小： 2
//                ================元素： 4
//                ================元素： 5
//                ================缓冲区大小： 1
//                ================元素： 5

    }

    private void useGroupBy() {
        //将发送的数据进行分组，每个分组都会返回一个被观察者
        Observable.just(5, 2, 3, 4, 1, 6, 8, 9, 7, 10)
                .groupBy(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer % 3;
                    }
                })
                .subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "====================onSubscribe ");
                    }

                    @Override
                    public void onNext(GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
                        Log.d(TAG, "====================onNext ");
                        integerIntegerGroupedObservable.subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "====================GroupedObservable onSubscribe ");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                Log.d(TAG, "====================GroupedObservable onNext  groupName: " + integerIntegerGroupedObservable.getKey() + " value: " + integer);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "====================GroupedObservable onError ");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "====================GroupedObservable onComplete ");
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "====================onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "====================onComplete ");
                    }
                });
//                ====================onSubscribe
//                ====================onNext
//                ====================GroupedObservable onSubscribe
//                ====================GroupedObservable onNext  groupName: 2 value: 5
//                ====================GroupedObservable onNext  groupName: 2 value: 2
//                ====================onNext    应该创建groupname为0的组了,没有,通过onNext创建
//                ====================GroupedObservable onSubscribe
//                ====================GroupedObservable onNext  groupName: 0 value: 3
//                ====================onNext    应该创建groupname为1的组了,没有,通过onNext创建
//                ====================GroupedObservable onSubscribe
//                ====================GroupedObservable onNext  groupName: 1 value: 4
//                ====================GroupedObservable onNext  groupName: 1 value: 1
//                ====================GroupedObservable onNext  groupName: 0 value: 6
//                ====================GroupedObservable onNext  groupName: 2 value: 8
//                ====================GroupedObservable onNext  groupName: 0 value: 9
//                ====================GroupedObservable onNext  groupName: 1 value: 7
//                ====================GroupedObservable onNext  groupName: 1 value: 10
//                ====================GroupedObservable onComplete
//                ====================GroupedObservable onComplete
//                ====================GroupedObservable onComplete
//                ====================onComplete

    }

    private void useScan() {
        //将数据以一定的逻辑聚合起来
        Observable.just(1, 2, 3, 4, 5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        Log.d(TAG, "====================apply ");
                        Log.d(TAG, "====================integer " + integer);
                        Log.d(TAG, "====================integer2 " + integer2);
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "====================accept " + integer);
                    }
                });
//                ====================accept 1
//                ====================apply
//                ====================integer 1
//                ====================integer2 2
//                ====================accept 3
//                ====================apply
//                ====================integer 3
//                ====================integer2 3
//                ====================accept 6
//                ====================apply
//                ====================integer 6
//                ====================integer2 4
//                ====================accept 10
//                ====================apply
//                ====================integer 10
//                ====================integer2 5
//                ====================accept 15
    }

    private void useWindow() {
        //发送指定数量的事件时，就将这些事件分为一组。window 中的 count 的参数就是代表指定的数量,
        //例如将 count 指定为2，那么每发2个数据就会将这2个数据分成一组
        Observable.just(1, 2, 3, 4, 5)
                .window(2)
                .subscribe(new Observer<Observable<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "=====================onSubscribe ");
                    }

                    @Override
                    public void onNext(Observable<Integer> integerObservable) {
                        integerObservable.subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.d(TAG, "=====================integerObservable onSubscribe ");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                Log.d(TAG, "=====================integerObservable onNext " + integer);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d(TAG, "=====================integerObservable onError ");
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "=====================integerObservable onComplete ");
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "=====================onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "=====================onComplete ");
                    }
                });
//         =====================onSubscribe
//         =====================integerObservable onSubscribe
//         =====================integerObservable onNext 1
//         =====================integerObservable onNext 2
//         =====================integerObservable onComplete
//         =====================integerObservable onSubscribe
//         =====================integerObservable onNext 3
//         =====================integerObservable onNext 4
//         =====================integerObservable onComplete
//         =====================integerObservable onSubscribe
//         =====================integerObservable onNext 5
//         =====================integerObservable onComplete
//         =====================onComplete

    }

    private void useConcat() {
        //将多个观察者组合到一起,最多支持4个组
        //ConcatArray 支持发送超过4个的事件
        //均是串行发送事件,error事件会中断事件的继续发送
        //mergeArrayDelayError 不会中断事件的发送,最后触发onError

        //merge  并行发送事件
        Observable.concat(Observable.just(1, 2),
                Observable.just(3, 4),
                Observable.just(5, 6),
                Observable.just(7, 8))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
//                ================onNext 1
//                ================onNext 2
//                ================onNext 3
//                ================onNext 4
//                ================onNext 5
//                ================onNext 6
//                ================onNext 7
//                ================onNext 8

    }

    private void useZip() {
        //将两个事件合并,最后发送的事件数量跟最少的保持一致
        Observable.zip(
                Observable.intervalRange(1, 5, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s1 = "A" + aLong;
                                Log.d(TAG, "===================A 发送的事件 " + s1);
                                return s1;
                            }
                        }),
                Observable.intervalRange(1, 6, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s2 = "B" + aLong;
                                Log.d(TAG, "===================B 发送的事件 " + s2);
                                return s2;
                            }
                        }),
                new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        String res = s + s2;
                        return res;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "===================onSubscribe ");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "===================onNext " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "===================onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "===================onComplete ");
                    }
                });
//         ===================onSubscribe
//         ===================A 发送的事件 A1
//         ===================B 发送的事件 B1
//         ===================onNext A1B1
//         ===================A 发送的事件 A2
//         ===================B 发送的事件 B2
//         ===================onNext A2B2
//         ===================A 发送的事件 A3
//         ===================B 发送的事件 B3
//         ===================onNext A3B3
//         ===================A 发送的事件 A4
//         ===================B 发送的事件 B4
//         ===================onNext A4B4
//         ===================A 发送的事件 A5
//         ===================B 发送的事件 B5
//         ===================onNext A5B5
//         ===================onComplete

    }

    private void useCombineLast() {
//        combineLatest() 的作用与 zip() 类似，但是 combineLatest() 发送事件的序列是与发送的时间线有关的，
//        当 combineLatest() 中所有的 Observable 都发送了事件，只要其中有一个 Observable 发送事件，这个事件就会和其他 Observable 最近发送的事件结合起来发送
        Observable.combineLatest(
                Observable.intervalRange(1, 4, 1, 1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s1 = "A" + aLong;
                                Log.d(TAG, "===================A 发送的事件 " + s1);
                                return s1;
                            }
                        }),
                Observable.intervalRange(1, 5, 2, 2, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                String s2 = "B" + aLong;
                                Log.d(TAG, "===================B 发送的事件 " + s2);
                                return s2;
                            }
                        }),
                new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        String res = s + s2;
                        return res;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "===================onSubscribe ");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "===================最终接收到的事件 " + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "===================onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "===================onComplete ");
                    }
                });
//        ===================onSubscribe
//        ===================A 发送的事件 A1
//        ===================A 发送的事件 A2
//        ===================B 发送的事件 B1
//        ===================最终接收到的事件 A2B1
//        ===================A 发送的事件 A3
//        ===================最终接收到的事件 A3B1
//        ===================A 发送的事件 A4
//        ===================B 发送的事件 B2
//        ===================最终接收到的事件 A4B1
//        ===================最终接收到的事件 A4B2
//        ===================B 发送的事件 B3
//        ===================最终接收到的事件 A4B3
//        ===================B 发送的事件 B4
//        ===================最终接收到的事件 A4B4
//        ===================B 发送的事件 B5
//        ===================最终接收到的事件 A4B5
//        ===================onComplete

    }

    private void useReduce() {
        //与 scan() 操作符的作用也是将发送数据以一定逻辑聚合起来，这两个的区别在于 scan() 每处理一次数据就会将事件发送给观察者,
        // 而 reduce() 会将所有数据聚合在一起才会发送事件给观察者。
        Observable.just(0, 1, 2, 3)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        int res = integer + integer2;
                        Log.d(TAG, "====================integer " + integer);
                        Log.d(TAG, "====================integer2 " + integer2);
                        Log.d(TAG, "====================res " + res);
                        return res;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "==================accept " + integer);
                    }
                });
//                ====================integer 0
//                ====================integer2 1
//                ====================res 1
//                ====================integer 1
//                ====================integer2 2
//                ====================res 3
//                ====================integer 3
//                ====================integer2 3
//                ====================res 6
//                ==================accept 6

    }

    private void useCollect() {
        //将数据收集到数据结构当中
        Observable.just(1, 2, 3, 4)
                .collect(new Callable<ArrayList<Integer>>() {
                             @Override
                             public ArrayList<Integer> call() throws Exception {
                                 return new ArrayList<>();
                             }
                         },
                        new BiConsumer<ArrayList<Integer>, Integer>() {
                            @Override
                            public void accept(ArrayList<Integer> integers, Integer integer) throws Exception {
                                integers.add(integer);
                            }
                        })
                .subscribe(new Consumer<ArrayList<Integer>>() {
                    @Override
                    public void accept(ArrayList<Integer> integers) throws Exception {
                        Log.d(TAG, "===============accept " + integers);
                    }
                });
//===============accept [1, 2, 3, 4]
    }

    private void useStartWith() {
        //追加事件,追加的事件会最先发出
        Observable.just(5, 6, 7)
                .startWithArray(2, 3, 4)//可以有多个事件
                .startWith(1)//单个事件
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "================accept " + integer);
                    }
                });
//                ================accept 1
//                ================accept 2
//                ================accept 3
//                ================accept 4
//                ================accept 5
//                ================accept 6
//                ================accept 7

    }

    private void useCount() {
        //返回被观察者发送的事件数量
        Observable.just(1, 2, 3)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "=======================aLong " + aLong);
                    }
                });
//=======================aLong 3
    }

    private void useFunc() {
        Observable.just(1, 2, 3)
                .delay(2, TimeUnit.SECONDS)//延时2s发送事件
                .doOnEach(new Consumer<Notification<Integer>>() {//每发送一次事件调用一次,包括onComplete和onError
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        Log.d(TAG, "==================doOnEach " + integerNotification.getValue());
                    }
                })
                .doOnNext(new Consumer<Integer>() {//每发送next事件前调用
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "==================doOnNext " + integer);
                    }
                })
                .doAfterNext(new Consumer<Integer>() {//next事件后调用
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "==================doAfterNext " + integer);
                    }
                })
                .doOnComplete(new Action() {//onComplete前调用
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "==================doOnComplete ");
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "==================doOnError " + throwable);
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(TAG, "==================doOnSubscribe ");
                    }
                })
                .doOnDispose(new Action() {//在调用Dispose的dispose方法后调用
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "==================doOnDispose ");
                    }
                })
                .doOnLifecycle(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //在onSubscribe之前调用
                        Log.d(TAG, "==================doOnLifecycle accept");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //取消订阅后调用
                        Log.d(TAG, "==================doOnLifecycle Action");
                    }
                })
                .doOnTerminate(new Action() {//在onComplete或者onError之前调用
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "==================doOnTerminate ");
                    }
                })
                .doFinally(new Action() {//所有事件发送完毕后调用
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "==================doOnFinally ");
                    }
                })
                .onErrorReturn(new Function<Throwable, Integer>() {//在onError调用后再调用一次onNext返回这个return值
                    @Override
                    public Integer apply(Throwable throwable) throws Exception {
                        return 404;
                    }
                })
                .retry()//当发生error时重发事件序列
                .retryUntil(new BooleanSupplier() {//在一定条件下进行retry
                    @Override
                    public boolean getAsBoolean() throws Exception {
                        return false;
                    }
                })
                .subscribeOn(Schedulers.newThread())//指定被观察者的执行线程,只有第一次调用有效
                .observeOn(AndroidSchedulers.mainThread())//指定观察者的执行线程,每调用一次生效一次


                .filter(new Predicate<Integer>() {//发送指定类型的事件,满足条件才会继续发送
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return false;
                    }
                })
                .ofType(Integer.class)//过滤掉不是Integer类型的事件
                .skip(2)//跳过的事件数量
                .distinct()//过滤掉事件中的重复事件
                .distinctUntilChanged()//过滤掉连续的重复事件
                .take(3) //指定观察者只接受3个事件
                .debounce(1, TimeUnit.SECONDS)//如果两个事件的发送间隔不超过1s就只发送最近的事件
                .subscribe(new Observer<Integer>() {
                    Disposable p;//可以取消订阅

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "=======================onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "=======================onNext " + integer);
                        p.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "=======================onSubscribe");
                    }
                });

    }

    private void useCondition() {
        Observable.just(1, 2, 3, 4)
                .all(new Predicate<Integer>() {//当满足所有条件再执行任务
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 5;
                    }
                })
                //.takeUntil()//满足条件发送数据
                //.isEmpty()//是否为空
                .contains(1)//是否包含
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG, "==================aBoolean " + aBoolean);
                    }
                });

    }


}
