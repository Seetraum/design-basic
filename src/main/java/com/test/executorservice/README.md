LinkedBlockingQueue
  锁&条件

    实现的队列中的锁是分离的，即生产用的是putLock，消费是takeLock

        吞吐相对更高

    两个条件（notFull、notEmpty）

  内存分配

    不会提前分配内存

    入队时，再分配内存（能更好的匹配内存碎片），并包装成 Node<E> 节点再入队

    使用过程中动态分配的

  大小

    无需提前指定（默认是Integer.MAX_VALUE）

        但也可以指定大小（变成有界队列）

    可以理解为无界的

        对于无界队列，当入列速度大于出列速度时可能会造成内存溢出

  公平性

    无法指定公平性

  方法

    入队

        共同点

                须获取到入队锁(putLock)

                若队列还未满，则随机唤醒一个在入队方法(put方法、offer的超时方法)中阻塞的线程

                notFull.signal()

        put

                成功

                返回 void

                失败

                （一直）阻塞当前线程

                notFull.await();

        offer(E e)

                成功

                返回true

                失败

                返回 false

        offer(E e, long timeout, TimeUnit unit)

                同put()方法一样，唯一不同的是 put方法是永久等待，而这个方法是超时等待。

    出队

        共同点

                须获取到出队锁(takeLock)

                成功时，若队列还非空，则随机唤醒一个在出队方法(take方法、poll的超时方法)中阻塞的线程

                notEmpty.signal();

        take()

                失败

                （一直）阻塞当前线程

                notEmpty.await();

        poll()

                失败

                返回 null

        poll(long timeout, TimeUnit unit)

                同take()方法一样，唯一不同的是：take方法是永久等待，而这个方法是超时等待

ArrayBlockingQueue 和 LinkedBlockingQueue 共有方法
  入队

    add

        成功

                return true

        失败

                队列已满，则抛出异常

  出队

    remove

        成功

                返回移除的元素

        失败

                队列已空，则抛出异常

  检查方法

    peek()

        获取但不移除此队列的头；如果此队列为空，则返回 null

    element()

        获取但不移除此队列的头元素，没有元素则抛异常

  
BlockingDeque
  Double Ended Queue

  在线程既是一个队列的生产者又是这个队列的消费者的时候可以使用

  实现

    LinkedBlockingDeque

  方法分类

    

PriorityBlockingQueue
  队列中的元素总是按照“自然顺序”进行排序， 或者根据构造函数中给定的Comparator进行排序

    不允许存在null，也不允许存在不能排序的元素

  队列容量是没有上限的

    如果插入的元素超过负载，有可能会引起OutOfMemory异常

    所以put方法是不会被阻塞的，但是take方法是会被阻塞的

  每次 take 取出的都是优先级最高的（compare 值最小的）

    但 iterator() 返回值不保证顺序

  通过完全二叉树（complete binary tree）实现 的小顶堆（任意一个非叶子节点的权值，都不大于其左右子节点的权值）

  比如发短信，通知短信和验证码短信， 一般验证码短信要最快发到客户手中， 通知类可以慢一点无碍，有验证码短信优先发送

DelayQueue
  延时优先级阻塞队列

    队列中只能存入Delayed接口实现的对象

    通过PriorityQueue， 队列头元素是最接近过期的元素（一般也存在了最久）， 使得最先过期的对象最先被处理

        在很多需要回收超时对象的场景都能用上

        等待一段时间之后关闭连接

        缓存对象过期删除

        下单之后如果三十分钟之内没有付款就自动取消订单

    队列中的对象按照优先级（按照compareTo）进行了排序， 队列头部是最先超时的对象

    当队列中对象的getDelay方法返回的值小于等于0（即对象已经超时）时， 才可以将对象从队列中取出

        若使用take方法，则方法会一直阻塞， 直到队列头部的对象超时被取出

   Delayed 接口

    public interface Delayed extends Comparable<Delayed> { long getDelay(TimeUnit unit); }

SynchronousQueue
  插入元素到队列的线程被阻塞， 直到另一个线程来接手待存储的元素

    是一个不存储元素的阻塞队列， 会直接将任务交给消费者

  队列没有容量Capacity（或者说容量为0）， 事实上队列中并不存储元素（入队线程自己持有元素）， 它只是提供两个线程进行信息交换的场所

    不能对元素进行迭代，不能peek元素，poll会返回null

     iterator() 永远返回空，因为里面没东西

    isEmpty()永远是true。

    一个元素就不会在SynchronousQueue 里面长时间停留， 一旦有了插入线程和移除线程，元素很快就从插入线程 “移交” 给移除线程。 也就是说这更像是一种信道（管道），资源从一个地方快速传递到另一地方

  队列中不允许存入null元素

  支持“公平”策略，在构造函数中可以传入false或true表示是否支持该策略

  Executors.newCachedThreadPool()(线程数不限)会用到该队列

    因为线程数量几乎无限（上限为Integer.MAX_VALUE）， 因此提交的任务只需要在SynchronousQueue队列中同步移交给空余线程即可

TransferQueue
  TransferQueue提供了一个场所， 生产者线程使用transfer方法传入一些对象并阻塞， 直至这些对象被消费者线程全部取出

    相比普通blockingQueue队列满时才阻塞， TransferQueue则更进一步， 生产者会一直阻塞直到所添加到队列的元素被某一个消费者所消费（不仅仅是添加到队列里就完事）

        它有效地实现了元素在线程之间的传递（transfer）

        在这样的设计中，消费者的消费能力将决定生产者产生消息的速度。

  SynchronousQueue很像一个容量为0的TransferQueue

  它是ConcurrentLinkedQueue, SynchronousQueue (在公平模式下), 无界的LinkedBlockingQueues等的超集

    性能比 LinkedBlockingQueue 更高（没有锁操作）， 比 SynchronousQueue能存储更多的元素

        SynchronousQueue 内部无法存储元素， 当要添加元素的时候，需要阻塞，不够完美

        LinkedBolckingQueue 则内部使用了大量的锁，性能不够高

  方法

    transfer(E e)

        若当前存在一个正在等待获取的消费者线程，即直接 “交给” 消费线程

        否则，会插入当前元素e到队列尾部， 并且等待进入阻塞状态，到有消费者线程取走该元素

    put

        transfer(E e)，不过不会等待

    tryTransfer(E e)

        若当前存在一个正在等待获取的消费者线程，即立刻移交之

        若不存在，则返回false，并且不进入队列

    tryTransfer(E e, long timeout, TimeUnit unit)

        若当前存在一个正在等待获取的消费者线程，即立刻移交之

        否则将插入元素e到队列尾部，并且等待被消费者线程获取消费掉

        若在指定的时间内元素e无法被消费者线程获取， 则返回false，同时该元素被移除

    hasWaitingConsumer()

    getWaitingConsumerCount()