1） **（必做）**思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程？ 

针对my_thread_demo，模式详见ThreadConstant，一共四种

执行代码详见Homework03 分别采用5个线程做的实验。

1.1）CyclicBarrierType方式，反馈结果如下

CyclicBarrierType:::线程组任务0已经结束
CyclicBarrierType:::线程组任务1已经结束
CyclicBarrierType:::线程组任务3已经结束
CyclicBarrierType:::线程组任务2已经结束
CyclicBarrierType:::线程组任务4已经结束
回调>>Thread-4
异步计算结果为：24157817
使用时间：27 ms
回调>>线程组执行结束

Process finished with exit code 0

1.2）CountDownLatchType方式，反馈如下

CountDownLatch:::线程组任务0已经结束
c:::线程组任务1已经结束
CountDownLatch:::线程组任务2已经结束
CountDownLatch:::线程组任务3已经结束
CountDownLatch:::线程组任务4已经结束
异步计算结果为：24157817
使用时间：7 ms
==>各个子线程执行结束。。。。
==>主线程执行结束。。。。

Process finished with exit code 0

1.3）SemaphoreToolsType方式，反馈如下：

SemaphoreToolsType:::线程组任务0获取结果
异步计算结果为：24157817
使用时间：78 ms
SemaphoreToolsType:::线程组任务0结束关闭

SemaphoreToolsType:::线程组任务1获取结果
异步计算结果为：24157817
使用时间：1078 ms
SemaphoreToolsType:::线程组任务1结束关闭

SemaphoreToolsType:::线程组任务2获取结果
异步计算结果为：24157817
使用时间：2079 ms
SemaphoreToolsType:::线程组任务2结束关闭

SemaphoreToolsType:::线程组任务3获取结果
异步计算结果为：24157817
使用时间：3079 ms
SemaphoreToolsType:::线程组任务3结束关闭

SemaphoreToolsType:::线程组任务4获取结果
异步计算结果为：24157817
使用时间：4079 ms
SemaphoreToolsType:::线程组任务4结束关闭

Process finished with exit code 0

1.4）SemaphoreNoToolsType方式，反馈如下

SemaphoreNoToolsType:::线程组任务1获取结果
异步计算结果为：24157817
使用时间：5 ms
SemaphoreNoToolsType:::线程组任务1结束关闭

SemaphoreNoToolsType:::线程组任务2获取结果
异步计算结果为：24157817
使用时间：6 ms
SemaphoreNoToolsType:::线程组任务2结束关闭
SemaphoreNoToolsType:::线程组任务4获取结果
异步计算结果为：24157817
使用时间：6 ms
SemaphoreNoToolsType:::线程组任务4结束关闭

SemaphoreNoToolsType:::线程组任务3获取结果
异步计算结果为：24157817
使用时间：6 ms
SemaphoreNoToolsType:::线程组任务3结束关闭

SemaphoreNoToolsType:::线程组任务0获取结果
异步计算结果为：24157817
使用时间：6 ms
SemaphoreNoToolsType:::线程组任务0结束关闭

Process finished with exit code 0

在Semaphore的玩法中，我都采用了串行所以线程执行的比较规整，

后续再通过修改令牌，不让其串行，试试怎么让他们达到上述的反馈结果。

2） 把多线程和并发相关知识带你梳理一遍，画一个脑图，截图上传到 Github 上。 

详见：多线程思维导图.png。

备注 ：找的图比较全面，也详细看过，其中缺少了CyclicBarrier，CountDown，Semaphore。

这个几个内容在之前的题目已经有演练，后续自己再次整理的时候，会按照该图和课件内容在画一幅图加上。