import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import components.naturalnumber.NaturalNumber;

/**
 * View class.
 *
 * @author Mohamed Jama
 */
//Used the same suppressWarnings on the DemoView1 to get rid of the NNCalcView1
//class not serializable warning
@SuppressWarnings("serial")
public final class NNCalcView1 extends JFrame implements NNCalcView {

    /**
     * Controller object registered with this view to observe user-interaction
     * events.
     */
    private NNCalcController controller;

    /**
     * State of user interaction: last event "seen".
     */
    private enum State {
        /**
         * Last event was clear, enter, another operator, or digit entry, resp.
         */
        SAW_CLEAR, SAW_ENTER_OR_SWAP, SAW_OTHER_OP, SAW_DIGIT
    }

    /**
     * State variable to keep track of which event happened last; needed to
     * prepare for digit to be added to bottom operand.
     */
    private State currentState;

    /**
     * Text areas.
     */
    private final JTextArea tTop, tBottom;

    /**
     * Operator and related buttons.
     */
    private final JButton bClear, bSwap, bEnter, bAdd, bSubtract, bMultiply,
            bDivide, bPower, bRoot;

    /**
     * Digit entry buttons.
     */
    private final JButton[] bDigits;

    /**
     * Useful constants.
     */
    private static final int TEXT_AREA_HEIGHT = 5, TEXT_AREA_WIDTH = 20,
            DIGIT_BUTTONS = 10, MAIN_BUTTON_PANEL_GRID_ROWS = 4,
            MAIN_BUTTON_PANEL_GRID_COLUMNS = 4, SIDE_BUTTON_PANEL_GRID_ROWS = 3,
            SIDE_BUTTON_PANEL_GRID_COLUMNS = 1, CALC_GRID_ROWS = 3,
            CALC_GRID_COLUMNS = 1, FOUR = 4, SEVEN = 7, TEN = 10;

    /**
     * No argument constructor.
     */
    public NNCalcView1() {
        // Create the JFrame being extended

        /*
         * Call the JFrame (superclass) constructor with a String parameter to
         * name the window in its title bar
         */
        super("Natural Number Calculator");

        // Set up the GUI widgets --------------------------------------------

        /*
         * Set up initial state of GUI to behave like last event was "Clear";
         * currentState is not a GUI widget per se, but is needed to process
         * digit button events appropriately. Initialized two areas to show the
         * top and bottom of the calculator by using final int constants.
         */
        this.currentState = State.SAW_CLEAR;
        this.tTop = new JTextArea("", TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);
        this.tBottom = new JTextArea("", TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);

        //Initialize my buttons for the NaturalNumber calculator operations.
        this.bClear = new JButton("Clear");
        this.bSwap = new JButton("Swap");
        this.bEnter = new JButton("Enter");
        this.bAdd = new JButton("+");
        this.bSubtract = new JButton("-");
        this.bMultiply = new JButton("*");
        this.bDivide = new JButton("/");
        this.bPower = new JButton("Power");
        this.bRoot = new JButton("Root");
        //Make digit button from 0 to 9 to be used in the calculator.
        this.bDigits = new JButton[DIGIT_BUTTONS];
        //For loop so that it minimizes the amount of this.bDigits[#] in my
        //code.
        for (int i = 0; i < DIGIT_BUTTONS; i++) {
            this.bDigits[i] = new JButton(Integer.toString(i));
        }
        /*
         * Create widgets
         */
        // Set up the GUI widgets --------------------------------------------

        /*
         * Text areas should wrap lines, and should be read-only; they cannot be
         * edited because allowing keyboard entry would require checking whether
         * entries are digits, which we don't want to have to do
         */
        this.tTop.setEditable(false);
        this.tBottom.setEditable(false);
        this.tTop.setLineWrap(true);
        this.tBottom.setLineWrap(true);
        this.tTop.setWrapStyleWord(true);
        this.tBottom.setWrapStyleWord(true);
        /*
         * Initially, the following buttons should be disabled: divide (divisor
         * must not be 0) and root (root must be at least 2) -- hint: see the
         * JButton method setEnabled and made sure that the root & divide ops
         * follow the constants above for my NN calculator.
         */
        this.bRoot.setEnabled(false);
        this.bDivide.setEnabled(false);

        /*
         * Created and added scroll panes for the text areas in case number is
         * long enough to require scrolling
         */
        JScrollPane inputScroll = new JScrollPane(this.tTop);
        JScrollPane outputScroll = new JScrollPane(this.tBottom);

        /*
         * Create main button panel
         */
        JPanel mButtomPanel = new JPanel(new GridLayout(
                MAIN_BUTTON_PANEL_GRID_ROWS, MAIN_BUTTON_PANEL_GRID_COLUMNS));
        /*
         * Add the buttons to the main button panel, from left to right and top
         * to bottom. A for loop to show three buttons and the add op to the top
         * left section and I also initialized final int numbers for 4, 7, 10
         */
        for (int i = SEVEN; i < TEN; i++) {
            mButtomPanel.add(this.bDigits[i]);
        }
        mButtomPanel.add(this.bAdd);
        //For loop to show the next three buttons and the subtract operation
        //right under the top loop.
        for (int i = FOUR; i < SEVEN; i++) {
            mButtomPanel.add(this.bDigits[i]);
        }
        mButtomPanel.add(this.bSubtract);
        //Another for loop to show the last 3 buttons plus the remaining operators.
        for (int i = 1; i < FOUR; i++) {
            mButtomPanel.add(this.bDigits[i]);
        }
        mButtomPanel.add(this.bMultiply);
        mButtomPanel.add(this.bDigits[0]);
        mButtomPanel.add(this.bPower);
        mButtomPanel.add(this.bRoot);
        mButtomPanel.add(this.bDivide);

        /*
         * Create side button panel
         */
        JPanel sButtomPanel = new JPanel(new GridLayout(
                SIDE_BUTTON_PANEL_GRID_ROWS, SIDE_BUTTON_PANEL_GRID_COLUMNS));
        /*
         * Added the clear, swap, and enter buttons to the side button panel,
         * from left to the right and top to bottom
         */
        sButtomPanel.add(this.bClear);
        sButtomPanel.add(this.bSwap);
        sButtomPanel.add(this.bEnter);
        /*
         * Create combined button panel organized using flow layout, which is
         * simple and does the right thing: sizes of nested panels are natural,
         * not necessarily equal as with grid layout
         */
        JPanel cButtonPanel = new JPanel(new FlowLayout());

        /*
         * Add the other two button panels to the combined button panel
         */

        cButtonPanel.add(mButtomPanel);
        cButtonPanel.add(sButtomPanel);
        /*
         * Organize main window
         */

        this.setLayout(new GridLayout(CALC_GRID_ROWS, CALC_GRID_COLUMNS));
        /*
         * Add scroll panes and button panel to main window, from left to right
         * and top to bottom
         */
        this.add(inputScroll);
        this.add(outputScroll);
        this.add(cButtonPanel);

        // Set up the observers ----------------------------------------------
        /*
         * Register this object as the observer for all GUI events with a for
         * loop to add action listeners to the 0-9 numbers on the calculator.
         */
        this.bClear.addActionListener(this);
        this.bSwap.addActionListener(this);
        this.bEnter.addActionListener(this);
        this.bAdd.addActionListener(this);
        this.bSubtract.addActionListener(this);
        this.bMultiply.addActionListener(this);
        this.bDivide.addActionListener(this);
        this.bPower.addActionListener(this);
        this.bRoot.addActionListener(this);

        for (int i = 0; i < DIGIT_BUTTONS; i++) {
            this.bDigits[i].addActionListener(this);
        }
        // Set up the main application window --------------------------------
        /*
         * Make sure the main window is appropriately sized, exits this program
         * on close, and becomes visible to the user
         */
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void registerObserver(NNCalcController controller) {

        //Made sure to register the controller here.
        this.controller = controller;

    }

    @Override
    public void updateTopDisplay(NaturalNumber n) {

        //Shows the result of the top part of my NN calculator.
        this.tTop.setText(n.toString());

    }

    @Override
    public void updateBottomDisplay(NaturalNumber n) {

        //Shows the result of the bottom part of my NN calculator.
        this.tBottom.setText(n.toString());

    }

    @Override
    public void updateSubtractAllowed(boolean allowed) {

        //Updates the subtract operation if it can be used or not.
        this.bSubtract.setEnabled(allowed);

    }

    @Override
    public void updateDivideAllowed(boolean allowed) {

        //Updates the division operation if it can be used or not.
        this.bDivide.setEnabled(allowed);

    }

    @Override
    public void updatePowerAllowed(boolean allowed) {

        //Updates the power operation if it can be used or not.
        this.bPower.setEnabled(allowed);

    }

    @Override
    public void updateRootAllowed(boolean allowed) {

        //Updates the toot operation if it can be used or not.
        this.bRoot.setEnabled(allowed);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        /*
         * Set cursor to indicate computation on-going; this matters only if
         * processing the event might take a noticeable amount of time as seen
         * by the user
         */
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        /*
         * Determine which event has occurred that we are being notified of by
         * this callback; in this case, the source of the event (i.e, the widget
         * calling actionPerformed) is all we need because only buttons are
         * involved here, so the event must be a button press; in each case,
         * tell the controller to do whatever is needed to update the model and
         * to refresh the view
         */
        Object source = event.getSource();
        if (source == this.bClear) {
            this.controller.processClearEvent();
            this.currentState = State.SAW_CLEAR;
        } else if (source == this.bSwap) {
            this.controller.processSwapEvent();
            this.currentState = State.SAW_ENTER_OR_SWAP;
        } else if (source == this.bEnter) {
            this.controller.processEnterEvent();
            this.currentState = State.SAW_ENTER_OR_SWAP;
        } else if (source == this.bAdd) {
            this.controller.processAddEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.bSubtract) {
            this.controller.processSubtractEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.bMultiply) {
            this.controller.processMultiplyEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.bDivide) {
            this.controller.processDivideEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.bPower) {
            this.controller.processPowerEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else if (source == this.bRoot) {
            this.controller.processRootEvent();
            this.currentState = State.SAW_OTHER_OP;
        } else {
            for (int i = 0; i < DIGIT_BUTTONS; i++) {
                if (source == this.bDigits[i]) {
                    switch (this.currentState) {
                        case SAW_ENTER_OR_SWAP:
                            this.controller.processClearEvent();
                            break;
                        case SAW_OTHER_OP:
                            this.controller.processEnterEvent();
                            this.controller.processClearEvent();
                            break;
                        default:
                            break;
                    }
                    this.controller.processAddNewDigitEvent(i);
                    this.currentState = State.SAW_DIGIT;
                    break;
                }
            }
        }
        /*
         * Set the cursor back to normal (because we changed it at the beginning
         * of the method body)
         */
        this.setCursor(Cursor.getDefaultCursor());
    }

}
