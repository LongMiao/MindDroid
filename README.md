# MindDroid
>关于思维导图的应用，现在已经覆盖了所有的平台（Windows/Mac/Linux/Web/Android/IOS）,它的确很有用。在对一款校园社交软件的设计中，
我准备打算引入一个技能图谱的概念，来描述这个平台上用户的技能表现。当然，技能图谱最好的展现方式就是利用思维导图了，本着不重复造轮子
的思想，我搜索着网上关于Android平台上是否有相似项目的实现，很遗憾，没有找到，所以就有了这个项目的诞生。当前的它结构单一、功能简单，
不过在今后不断完善的过程中，谁又能保证会不会玩出什么新的花样呢？

##MindDroid 1.0版本支持功能
* 思维导图（右结构图）的基本展示
* 树形结构中节点可以无限制嵌套
* 每个节点支持添加点击事件
* 动态向思维导图树种添加节点

##实现原理
众所周知，Android展现视图最重要的三个步骤是：measure、layout、draw，其对应的回调函数分别为：onMeasure、onLayout、onDraw。思维
导图的树形结构和Android的View（也是树形结构）有着天然的匹配优势。程序设计了两个对象，一个MindNode，继承自FrameLayout,来模拟导图
中的节点，另一个是MindNodeGroup，它是作为MindNode和子MindNode的容器，继承自ViewGroup，MindNodeGroup在measure过程中，先遍历测
量子类视图的高度和宽度（系统会递归执行onMeasure），然后得出自己的宽度和高度；layout过程中，按照思维导图的布局规则遍历布局子类视图
的坐标。最后到了绘制的阶段，也就是连接节点和节点那些弯曲的线，这里用到了贝塞尔弧线。

##截图
![image](https://github.com/LongMiao/MindDroid/raw/master/screenshots/Screenshot.gif)