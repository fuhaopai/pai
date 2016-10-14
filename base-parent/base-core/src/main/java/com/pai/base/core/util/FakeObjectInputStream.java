package com.pai.base.core.util;

import java.io.IOException;  
import java.io.InputStream;  
import java.io.ObjectInputStream;  
import java.io.ObjectStreamClass;  
   
public class FakeObjectInputStream extends ObjectInputStream{  
   
public FakeObjectInputStream(InputStream in) throws IOException {  
  super(in);  
}  
public FakeObjectInputStream() throws SecurityException, IOException{  
  super();  
}  
@Override  
protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException{  
  ObjectStreamClass objInputStream = super.readClassDescriptor();  
  Class<?> localClass = Class.forName(objInputStream.getName());  
  ObjectStreamClass localInputStream = ObjectStreamClass.lookup(localClass);  
  if(localInputStream != null){  
    final long localUID = localInputStream.getSerialVersionUID();  
    final long objUID = objInputStream.getSerialVersionUID();  
    if(localUID != objUID){  
      return localInputStream;  
    }  
  }  
  return objInputStream;  
}  
}  
