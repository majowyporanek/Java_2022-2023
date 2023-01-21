import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FieldInspector implements FieldInspectorInterface {
    private Collection<FieldInfo>fieldsInfo;
    private Field[]fields;

    FieldInspector(){
        fieldsInfo = new ArrayList<>();
    }
    @Override
    public Collection<FieldInfo> inspect(String className) {
        try {
            Class<?> c = Class.forName(className);
            fields = c.getDeclaredFields();
            for(Field f : fields){
                if(processField(f)!=null){
                    fieldsInfo.add(processField(f));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return fieldsInfo;
    }

    public Type typeOfField(String fieldType){
        if(fieldType.toLowerCase().equals("integer") || fieldType.toLowerCase().equals("int")){
            return Type.INT;
        }else if(fieldType.toLowerCase().equals("long")){
            return Type.LONG;
        }else if(fieldType.toLowerCase().equals("float")){
            return Type.FLOAT;
        }else if(fieldType.toLowerCase().equals("double")){
            return Type.DOUBLE;
        }else {
            return Type.OTHER;
        }
    }

    public  FieldInfo processField(Field field){
        FieldInfo returnFile;
        String filename;
        Type fieldType;
        FieldVersion fieldVersion;
        int version;
        // check if is public
        if(!Modifier.isPublic(field.getModifiers())) {
            return null;}
        else {
            // get type:
            String type = field.getGenericType().getTypeName();
            if(type.contains(".")){
                type = type.substring(10);
            }
            fieldType = typeOfField(type);
            filename = field.getName();
            // check adnotation:
            if(field.isAnnotationPresent(FieldVersion.class)){
                fieldVersion = field.getAnnotation(FieldVersion.class);
                version = fieldVersion.version();
                returnFile = new FieldInfo(fieldType, filename, version);
                return returnFile;
            }else {
                returnFile =  new FieldInfo(fieldType, filename);
                return returnFile;
            }
        }
    }

//    public static void main(String[] args) {
//        FieldInspector fi = new FieldInspector();
//        Collection<FieldInfo>files = fi.inspect("TestClass");
//        for(FieldInfo f: files){
//            System.out.println(f.name);
//            System.out.println(f.type);
//            System.out.println(f.version);
//        }
////
////        System.out.println("Long".toLowerCase());
//    }

}
