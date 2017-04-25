def var = 1
def str = "i am a person"

String function(arg1, args2) {//无需指定参数类型
}

def nonReturnTypeFunc() {
    last_line   //最后一行代码的执行结果就是本函数的返回值
}
//如果指定了函数返回类型，则可不必加def关键字来定义函数
String getString() {
    return "I am a string"
}

//下面这个函数的返回值是字符串"getSomething return value"
def getSomething() {
    "getSomething return value" //如果这是最后一行代码，则返回类型为String
    1000 //如果这是最后一行代码，则返回类型为Integer
}

//print getSomething()

def singleQuote = 'I am $ dolloar'  //输出就是I am $ dolloar
def doubleQuoteWithoutDollar = "I am one dollar" //输出 I am one dollar
def x = 1
def doubleQuoteWithDollar = "I am $x dolloar" //输出I am 1 dolloar

//'''支持任意换行
def multieLines = ''' begin
   line  1
   line  2
   end '''

//print multieLines

//变量定义：List变量由[]定义，比如
def aList = [5, 'string', true] //List由[]定义，其元素可以是任何对象

//变量存取：可以直接通过索引存取，而且不用担心索引越界。如果索引超过当前链表长度，List会自动往该索引添加元素

assert aList[1] == 'string'
assert aList[5] == null //第6个元素为空
aList[100] = 100  //设置第101个元素的值为10
assert aList[100] == 100

//println 'aList的Size为:' + aList.size//现在aList的size变为101

//变量定义：Map变量由[:]定义，比如
def aMap = ['key1': 'value1', 'key2': true]

//Map由[:]定义，注意其中的冒号。冒号左边是key，右边是Value。key必须是字符串，value可以是任何对象。另外，key可以用''或""包起来，也可以不用引号包起来。比如
def aNewMap = [key1: "value", key2: true] //其中的key1和key2默认被处理成字符串"key1"和"key2"

//不过Key要是不使用引号包起来的话，也会带来一定混淆，比如

def key1 = "wowo"
def aConfusedMap = [key1: "who am i?"]//aConfuseMap中的key1到底是"key1"还是变量key1的值“wowo”？显然，答案是字符串"key1"。
// 如果要是"wowo"的话，则aConfusedMap的定义必须设置成：def aConfusedMap=[(key1):"who am i?"]

//Map中元素的存取更加方便，它支持多种方法：
println aMap.key1   // <==这种表达方法好像key就是aMap的一个成员变量一样
println aMap['key2'] //<==这种表达方法更传统一点
aMap.anotherkey = "i am map" // <==为map添加新元素
println aMap['anotherkey']

//Range是Groovy对List的一种拓展
def aRange = 1..5  //<==Range类型的变量 由begin值+两个点+end值表示,左边这个aRange包含1,2,3,4,5这5个值,如果不想包含最后一个元素，则

def aRangeWithoutEnd = 1..<5  //<==包含1,2,3,4这4个元素
println aRange.from
println aRange.to

//闭包
//def xxx = {paramters -> code}  //或者
//def xxx = {无参数，纯code}  这种case不需要->符号

def aClosure = {
        //闭包是一段代码，所以需要用花括号括起来..
    String param1, int param2 ->  //这个箭头很关键。箭头前面是参数定义，箭头后面是代码
        println "this is code" //这是代码，最后一句是返回值，
        //也可以使用return，和Groovy中普通函数一样
}

def greeting = { "Hello, $it!" } // == def greeting = {this->"Hello,$it!"}
def noParamClosure = { -> true }

def testClosure(int a1, String b1, Closure closure) {
    //do something
    closure() //调用闭包
}
//那么调用的时候，就可以免括号！
testClosure(4, "test", {
    println "i am in closure"
})  //当函数的最后一个参数是闭包的话，可以省略圆括号

//使用groovyc -d classes study.groovy 编译study这个类

def Closure printAuthorInfo = {
    String name, String gender, int age ->
        println "name->$name gender->$gender age->$age"
}


def ScriptTest base = new ScriptTest()
base.printInfo()
printAuthorInfo.call(base.author, base.gender, base.age) //都能拿到成员变量

//groovy中的文件操作
def File targetfile = new File("README.MD")
//targetfile.eachLine(
//        "utf-8",{//读取每一行
//    String line->
//        println line
//})

//println targetfile.getBytes()//直接得到文件所有内容,返回byte[]

//def ism =  targetfile.newInputStream()
//targetfile.eachLine{
//    String line->
//        println line
//}
//
//targetfile.withInputStream{
//    InputStream inputStream ->
//        println inputStream.getText()//操作ism. 不用close。Groovy会自动替你close
//}

def srcFile = new File('file.txt')
def targetFile = new File('outputGroovy.txt')
targetFile.withOutputStream {
    os ->
        srcFile.withInputStream {
            ins ->
                os << ins //利用OutputStream的<<操作符重载，完成从inputstream到OutputStream  //的输出
        }
}

new File("file.txt").eachLine {// 打开和读取文件的每一行
    println 'eachLine' + it.toUpperCase();
}

lineList = new File("file.txt").readLines();//把文件行读到一个 List 中
lineList.each {
    println 'readLines' + it.toUpperCase();
}

new File("file.txt").splitEachLine(",") {
    println "splitEachLine : name=${it[0]} balance=${it[1]}";
}

new File("file.txt").eachByte { print 'eachByte' + it; }

byteList = new File("file.txt").readBytes();
byteList.each {
    println 'readBytes' + it;
}

//new File("foo.txt").write("testing testing");
//new File("foo.txt").append("""/
//This is
//just a test file
//to play withff
//"""
//);

new File(".").eachFileRecurse {   //这里的 File 表示的是一个路径
    println it.getPath();  //eachFile() 列出的每一项是一个 File 实例
}