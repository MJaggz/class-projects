import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Mohamed Jama
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model,
            NNCalcView view) {

        //Initialize NaturalNumbers of top and bottom numbers from the model.
        NaturalNumber inputNaturalNumber = model.top();
        NaturalNumber outputNaturalNumber = model.bottom();

        //Update the view for the top and bottom numbers.
        view.updateTopDisplay(inputNaturalNumber);
        view.updateBottomDisplay(outputNaturalNumber);

        //Update the root operation's usability so that it only works if my
        //outputNaturalNumber is between my two final NaturalNumber constants.
        view.updateRootAllowed(outputNaturalNumber.compareTo(INT_LIMIT) <= 0
                && outputNaturalNumber.compareTo(TWO) >= 0);

        //Update the power operation's usability so that it only works if my
        //OutputNaturalNumber is less than or equal to my final constant of
        //INT_LIMIT.
        view.updatePowerAllowed(outputNaturalNumber.compareTo(INT_LIMIT) <= 0);

        //Update the subtract operation's usability so that it only works if my
        //outputNaturalNumber is less than or equal to my inputNaturalNumber.
        view.updateSubtractAllowed(
                outputNaturalNumber.compareTo(inputNaturalNumber) <= 0);

        //Update the divide operation's usability so that the button will be
        //pressable if my outputNaturalNumber variable is not zero.
        view.updateDivideAllowed(!outputNaturalNumber.isZero());

    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        //NaturalNumber bottom = this.model.bottom();
        NaturalNumber outputNaturalNumber = this.model.bottom();
        /*
         * Update model in response to this event
         */
        //bottom.clear();
        outputNaturalNumber.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber inputNaturalNumber = this.model.top();
        NaturalNumber outputNaturalNumber = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = inputNaturalNumber.newInstance();
        temp.transferFrom(inputNaturalNumber);
        inputNaturalNumber.transferFrom(outputNaturalNumber);
        outputNaturalNumber.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {

        //Start off by getting the top and bottom numbers from the model.
        NaturalNumber inputNaturalNumber = this.model.top();
        NaturalNumber outputNaturalNumber = this.model.bottom();

        //Copy the outputNaturalnumber which is the bottom to my
        //inputNaturalNumber variable and clear the bottom number.
        inputNaturalNumber.copyFrom(outputNaturalNumber);

        //Update the view to show the Enter event.
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {

        //Start off by getting the top and bottom numbers from the model.
        NaturalNumber inputNaturalNumber = this.model.top();
        NaturalNumber outputNaturalNumber = this.model.bottom();

        //Add outputNaturalNumber to inputNaturalNumber.
        inputNaturalNumber.add(outputNaturalNumber);

        //I used transferFrom to get the result to my outputNaturalNumber
        //variable.
        outputNaturalNumber.transferFrom(inputNaturalNumber);

        //Update the view to show the addition result.
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processSubtractEvent() {

        //Start off by getting the top and bottom numbers from the model.
        NaturalNumber inputNaturalNumber = this.model.top();
        NaturalNumber outputNaturalNumber = this.model.bottom();

        //Subtract outputNaturalNumber from inputNaturalNumber.
        inputNaturalNumber.subtract(outputNaturalNumber);

        //Use transeferFrom to move the result to my outputNaturalNumber
        //variable.
        outputNaturalNumber.transferFrom(inputNaturalNumber);

        //Update the view to show the subtraction result.
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {

        //Start off by getting the top and bottom numbers from the model.
        NaturalNumber inputNaturalNumber = this.model.top();
        NaturalNumber outputNaturalNumber = this.model.bottom();

        //Multiply by two NaturalNumber variables.
        inputNaturalNumber.multiply(outputNaturalNumber);

        //Move the result to my outputNaturalNumber variable.
        outputNaturalNumber.transferFrom(inputNaturalNumber);

        //Update the view to show the multiplication result.
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {

        //Start off by getting the top and bottom numbers from the model.
        NaturalNumber inputNaturalNumber = this.model.top();
        NaturalNumber outputNaturalNumber = this.model.bottom();

        //I made a new NaturalNumber to hold the remainder of by two variables.
        NaturalNumber remainderNaturalNumber = inputNaturalNumber
                .divide(outputNaturalNumber);

        //Move the quotient to outputNaturalNumber.
        outputNaturalNumber.transferFrom(inputNaturalNumber);
        //Transfer the remainder to inputNaturalNumber.
        inputNaturalNumber.transferFrom(remainderNaturalNumber);

        //Update the view to show the division result.
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processPowerEvent() {

        //Start off by getting the top and bottom numbers from the model.
        NaturalNumber inputNaturalNumber = this.model.top();
        NaturalNumber outputNaturalNumber = this.model.bottom();

        //Raise my inputNaturalNumber variable to the power of my
        //outputNaturalNumber variable while converting my outputNaturalNumber
        //to an integer.
        inputNaturalNumber.power(outputNaturalNumber.toInt());

        //Transfer my result to outputNaturalNumber.
        outputNaturalNumber.transferFrom(inputNaturalNumber);

        //Update the view to show the power result.
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {

        //Start off by getting the top and bottom numbers from the model.
        NaturalNumber inputNaturalNumber = this.model.top();
        NaturalNumber outputNaturalNumber = this.model.bottom();

        //Convert outputNaturalNumber to an integer so that it can be used when
        //finding the root.
        inputNaturalNumber.root(outputNaturalNumber.toInt());

        //Move the root result to outputNaturalNumber.
        outputNaturalNumber.transferFrom(inputNaturalNumber);

        //Update the view to show the root result.
        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {

        //Get the bottom number from the model.
        NaturalNumber outputNaturalNumber = this.model.bottom();

        //Basically add the digit to the end of outputNaturalNumber.
        outputNaturalNumber.multiplyBy10(digit);

        //Update the view to show the new bottom number in the calculator.
        updateViewToMatchModel(this.model, this.view);

    }

}
