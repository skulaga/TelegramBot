import java.util.List;

public class Test {
    public static void main(String[] args) {
        IrregularVerbs irregularVerbs = new IrregularVerbs();
        // System.out.println(irregularVerbs.readFile());
        // System.out.println(irregularVerbs.readIrregularVerbs());
//        List<String[]> strings = irregularVerbs.readIrregularVerbs();
//        for (String[] string : strings) {
//            for (int i = 0; i < string.length; i++) {
//                System.out.print(string[i] + " ");
//
//            }
//            System.out.println();
//        }

        List<ModelVerbs> modelVerbsList = irregularVerbs.readIrrVerbs();
        for (ModelVerbs modelVerbs : modelVerbsList) {
            System.out.println(modelVerbs);
        }
    }
}
