public class PrimitiveDataTypes {
    public static void main(String[] args) {
        // String is a reference type, not a primitive.
        String name = "Priyasha";
        System.out.println("name = " + name);

        // char is a 16-bit Unicode character.
        char ch = 'A';
        char hindi = 'अ';
        char emoji = '\u263A';
        System.out.println("ch = " + ch);
        System.out.println("hindi = " + hindi);
        System.out.println("emoji = " + emoji);

        // float requires the 'f' suffix because numeric literals are double by default.
        float f = 3.14f;
        // double is the default type for decimal literals.
        double d = 3.14;
        double d2 = 3.14d;
        System.out.println("float f = " + f);
        System.out.println("double d = " + d);
        System.out.println("double d2 = " + d2);

        // boolean can only be true or false.
        boolean flag = true;
        System.out.println("flag = " + flag);

        // Floating-point arithmetic may introduce precision errors.
        // This is why float/double are not ideal for currency calculations.
        System.out.println("float precision test: 0.3f - 0.1f = " + (0.3f - 0.1f));
    }
}
