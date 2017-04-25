import groovy.transform.Field;
@Field author = 'EvilsouM'
@Field gender = 'male'
@Field age = 25
//用了filed 就相当这就是一个class 就不用再自己定义class了)
def printInfo() {
    println "name->$author  gender->$gender age->$age"
}