test:
#这个test,make的时候执行make test即可,里面写shell命令
#<target> : <prerequisites>
#[tab]  <commands>
#上面第一行冒号前面的部分，叫做"目标"（target），
#冒号后面的部分叫做"前置条件"（prerequisites）;
#第二行必须由一个tab键起首，后面跟着"命令"（commands)
#不要乱用TAB,在只有需要执行命令的地方使用TAB,否则会提示missing separator
	@echo "this is test!"
