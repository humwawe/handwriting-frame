package hum.proxy;
import java.lang.reflect.Method;
public class $Proxy0 implements hum.obj.Person{
InvocationHandler h;
public $Proxy0(InvocationHandler h) {
this.h=h;
}public void eat() throws Throwable {
Method md = hum.obj.Person.class.getMethod("eat",new Class[]{});
this.h.invoke(this,md,null);
}
public void eat2() throws Throwable {
Method md = hum.obj.Person.class.getMethod("eat2",new Class[]{});
this.h.invoke(this,md,null);
}

}