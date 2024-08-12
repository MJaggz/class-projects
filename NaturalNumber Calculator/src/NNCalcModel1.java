import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Model class.
 *
 * @author Mohamed Jama
 */
public final class NNCalcModel1 implements NNCalcModel {

    /**
     * Model variables.
     */
    private final NaturalNumber inputNaturalNumber, outputNaturalNumber;

    /**
     * No argument constructor.
     */
    public NNCalcModel1() {
        //Makes the names for the top and bottom screens of the NaturalNumber
        //Calculator.
        this.inputNaturalNumber = new NaturalNumber2();
        this.outputNaturalNumber = new NaturalNumber2();
    }

    @Override
    public NaturalNumber top() {
        //Returns the number that is on the top section of the calculator screen
        return this.inputNaturalNumber;
    }

    @Override
    public NaturalNumber bottom() {
        //Returns the number that is on the bottom section of the screen.
        return this.outputNaturalNumber;
    }

}
