import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModelVerbs {
    private String firstForm;
    private String secondForm;
    private String thirdForm;
    private String russianForm;

    public ModelVerbs(String firstForm, String secondForm, String thirdForm, String russianForm) {
       setFirstForm(firstForm);
        setSecondForm(secondForm);
        setThirdForm(thirdForm);
        setRussianForm(russianForm);
    }
}
