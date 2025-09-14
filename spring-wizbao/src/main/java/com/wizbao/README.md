Spring解决循环依赖的方式:
1. 构造器(构造函数依赖注入)不能解决的
2. 多例的(prototype原型的)不能解决的(因为多例的bean无法进行缓存，也就谈不上三级缓存，也就谈不上解决了)
3. setter注入。能够解决。解决方式-三级缓存。如果存在这种依赖关系的对象，他会提前暴露一个factory工厂的入口(a和b相互依赖，初始化a发现没有b，初始化b发现没有a。
提前将我们的a和b通过factorybean的形式进行一个暴露，此时的暴露并没有真正的进行对象的创建，延迟创建，必须等到调用getObject方法的时候，才会真正的进行对象的创建。)
对于提前暴露来说，他能够使我们互相有依赖的对象找到一个引用，a发现没有b的实例，但是有b暴露的facotry接口，那么对于a来说，就相当于找到了b，即便此时b并没有真正的初始化。但是通过get0bject方法的调用，最终完成b的实例化，最终将a的依赖属性补充完整。
         
对于Spring中bean的类型:
1. 普通的bean:通过@Component，@Service啊这种注解进行bean的注册
2. factoryBean:他需要通过我们的getobject方法的回调，进行我们对象的创建，而且如果我们想要获取factorybean的话，我们在使用getBean方法的时候，需要在 beanname前边加上一个&。getBean(“&beanname”)

CreateBean方法
1. 通过我们beandefinition找到当前创建bean的 class
2. 寻找方法的重载
3. // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance
   Object bean =resolveBeforeInstantiation(beanName，mbdToUse);给AOp机会啊?也就成为了aop的入口
   aop源码的入口找到了，接下来我们就看看，使用了什么方法进行了我们的 aop的创建。(这部分源码比较复杂，但是我们可以通过
   阅读这部分源码的核心代码对aop代理的创建进行一个学习)。 面试问：aop 的功能，是在什么时候被执行的。答:BeanPostProcessors中进行的aop的实现
4. doCreateBean的调用:实例化--暴露三级缓存ObjectFactory --属性填充-- bean的初始化(调用init method) I