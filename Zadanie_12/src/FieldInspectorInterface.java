import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Collection;

/**
 * Interfejs systemu inspekcji pĂłl klasy.
 */
public interface FieldInspectorInterface {
    /**
     * Typ wyliczeniowy reprezentujÄcy wybrane typy danych
     * w Java.  Uwaga typy pasujÄ zarĂłwno do typĂłw prymitywnych jak i ich
     * wrapperĂłw: np. typ Type.INT odpowiada zarĂłwno
     * typowi int jak i Integer. OTHER naleĹźy uĹźyÄ w przypadku braku
     * moĹźliwoĹci uĹźycia lepszego (dokĹadniejszego) opisu.
     */
    public enum Type {
        INT, LONG, FLOAT, DOUBLE, OTHER;
    }

    /**
     * Klasa opisu pola klasy.
     */
    public class FieldInfo {
        /**
         * Typ pola.
         */
        public final Type type;
        /**
         * Nazwa pola
         */
        public final String name;
        /**
         * Numer wersji przepisany z adnotacji @FieldVersion
         */
        public final int version;

        /**
         * Konstruktor uĹźywany do przedstawienia pĂłl znaczonych adnotacjÄ
         * FieldVersion
         * @param type typ pola
         * @param name nazwa pola
         * @param version numer wersji
         */
        public FieldInfo(Type type, String name, int version) {
            this.type = type;
            this.name = name;
            this.version = version;
        }

        /**
         * Konstruktor uĹźywany do przedstawienia pĂłl, ktĂłre nie sÄ oznaczone
         * adnotacjÄ FieldVersion
         * @param type typ
         * @param name nazwa pola
         */
        public FieldInfo(Type type, String name) {
            this(type, name, -1);
        }
    }

    /**
     * Adnotacja z numerem wersji.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface FieldVersion {
        public int version();
    }

    /**
     * Metoda wykonuje inspekcjÄ klasy o podanej nazwie. Inspekcja polega na
     * sporzÄdzeniu kolekcji obiektĂłw FieldInfo. Kolekcja ma zawieraÄ wszystkie
     * publiczne pola zadeklarowane w analizowanej klasie. Pola obdarzone adnotacjÄ
     * typu FieldVersion majÄ mieÄ ustawionÄ w tej adnotacji wartoĹÄ version (dla
     * innych version ma mieÄ wartoĹÄ domyĹlnÄ).
     *
     * @param className nazwa analizowanej klasy
     * @return kolekcja obiektĂłw opisujacych publiczne pola zadeklarowane w
     *         analizowanej klasie.
     */
    public Collection<FieldInfo> inspect(String className);
}