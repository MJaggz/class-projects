import java.util.Iterator;

import components.binarytree.BinaryTree;
import components.binarytree.BinaryTree1;
import components.set.Set;
import components.set.SetSecondary;

/**
 * {@code Set} represented as a {@code BinaryTree} (maintained as a binary
 * search tree) of elements with implementations of primary methods.
 *
 * @param <T>
 *            type of {@code Set} elements
 * @mathdefinitions <pre>
 * IS_BST(
 *   tree: binary tree of T
 *  ): boolean satisfies
 *  [tree satisfies the binary search tree properties as described in the
 *   slides with the ordering reported by compareTo for T, including that
 *   it has no duplicate labels]
 * </pre>
 * @convention IS_BST($this.tree)
 * @correspondence this = labels($this.tree)
 *
 * @author Emanuel Messele and Mohamed Jama
 *
 */
public class Set3a<T extends Comparable<T>> extends SetSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private BinaryTree<T> tree;

    /**
     * Returns whether {@code x} is in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be searched for
     * @return true if t contains x, false otherwise
     * @requires IS_BST(t)
     * @ensures isInTree = (x is in labels(t))
     */
    private static <T extends Comparable<T>> boolean isInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        //initializing a boolean variable and setting it to false.
        boolean isInTree = false;

        //checks that the tree is not empty
        if (t.size() > 0) {
            //Initializing left and right subtrees while also disassembling them.
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);
            //Compares 'x' to the root, if they are equal then the boolean check
            //is true.
            int compareTo = x.compareTo(root);
            if (compareTo == 0) {
                isInTree = true;
                //If smaller than the root, it enters the left subtree.
            } else if (compareTo < 0) {
                isInTree = isInTree(left, x);
                //Else if greater, enters the right subtree.
            } else {
                isInTree = isInTree(right, x);
            }

            //reassembling the binary tree.
            t.assemble(root, left, right);
        }

        //returning isInTrue;
        return isInTree;
    }

    /**
     * Inserts {@code x} in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} to be searched
     * @param x
     *            the label to be inserted
     * @aliases reference {@code x}
     * @updates t
     * @requires IS_BST(t) and x is not in labels(t)
     * @ensures IS_BST(t) and labels(t) = labels(#t) union {x}
     */
    private static <T extends Comparable<T>> void insertInTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";

        if (t.size() > 0) {
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            T root = t.disassemble(left, right);
            //Compares x root to the root, and recursively checks if x is smaller
            //or larger and places x into its correct position.
            /*
             * compares x to the root, it enters into left or right tree
             * recursively depending on if x is smaller or larger.
             */
            int compareTo = x.compareTo(root);
            if (compareTo < 0) {
                insertInTree(left, x);
            } else {
                insertInTree(right, x);
            }
            //Reassembles the binary tree.
            t.assemble(root, left, right);
        } else {
            //disassembles t then reassembles with now 'x' as the node leaf
            BinaryTree<T> left = t.newInstance();
            BinaryTree<T> right = t.newInstance();
            t.assemble(x, left, right);
        }

    }

    /**
     * Removes and returns the smallest (left-most) label in {@code t}.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove the label
     * @return the smallest label in the given {@code BinaryTree}
     * @updates t
     * @requires IS_BST(t) and |t| > 0
     * @ensures <pre>
     * IS_BST(t)  and  removeSmallest = [the smallest label in #t]  and
     *  labels(t) = labels(#t) \ {removeSmallest}
     * </pre>
     */
    private static <T> T removeSmallest(BinaryTree<T> t) {
        assert t != null : "Violation of: t is not null";
        assert t.size() > 0 : "Violation of: |t| > 0";

        //Creating instances for left and right subtrees while also disassembling
        //the binary tree.
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();
        T root = t.disassemble(left, right);
        //If the left subtree is not empty, recursively find the smallest in the
        //left.

        if (left.size() > 0) {
            T smallRoot = removeSmallest(left);
            //reassembles the tree
            t.assemble(root, left, right);
            root = smallRoot;
        } else {
            //Else right will have the new value of 't'
            t.transferFrom(right);
        }
        //returns the root which is now the smallest value in the tree.
        return root;
    }

    /**
     * Finds label {@code x} in {@code t}, removes it from {@code t}, and
     * returns it.
     *
     * @param <T>
     *            type of {@code BinaryTree} labels
     * @param t
     *            the {@code BinaryTree} from which to remove label {@code x}
     * @param x
     *            the label to be removed
     * @return the removed label
     * @updates t
     * @requires IS_BST(t) and x is in labels(t)
     * @ensures <pre>
     * IS_BST(t)  and  removeFromTree = x  and
     *  labels(t) = labels(#t) \ {x}
     * </pre>
     */
    private static <T extends Comparable<T>> T removeFromTree(BinaryTree<T> t, T x) {
        assert t != null : "Violation of: t is not null";
        assert x != null : "Violation of: x is not null";
        assert t.size() > 0 : "Violation of: x is in labels(t)";

        //Creating instances for left and right subtrees.
        BinaryTree<T> left = t.newInstance();
        BinaryTree<T> right = t.newInstance();

        //Initialize a boolean check and set it as false which will check if
        //needed to transfer the left subtree to t.
        boolean transferLeft = false;
        //Variable that will hold the removed.
        T removed;

        //Disassemble the binary tree.
        T root = t.disassemble(left, right);

        //If x is smaller than the root, recursively remove 'x' from the left
        //subtree.
        if (x.compareTo(root) < 0) {
            removed = removeFromTree(left, x);
            //Else if x is greater than the root, recursively remove 'x' from the
            //right subtree.
        } else if (x.compareTo(root) > 0) {
            removed = removeFromTree(right, x);
        } else {
            //Else we found the node that needs to be removed.
            removed = root;
            //If the right subtree is empty, transfer the left subtree to 't'
            if (right.size() == 0) {
                transferLeft = true;
                t.transferFrom(left);
            } else {
                //Else replace the root with the smallest element from the right subtree.
                root = removeSmallest(right);
            }
        }
        //If the binary tree was never disassembled, reassemble with the updated
        //root.
        if (!transferLeft) {
            t.assemble(root, left, right);
        }
        //Return the removed label.
        return removed;
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        //Creating a representation of Set by making this.Tree an empty binary
        //tree.
        this.tree = new BinaryTree1<T>();

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Set3a() {

        //Creating a new representation for Set3a.
        this.createNewRep();

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final Set<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Set<T> source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof Set3a<?>
                : "" + "Violation of: source is of dynamic type Set3<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type Set3a<?>, and
         * the ? must be T or the call would not have compiled.
         */
        Set3a<T> localSource = (Set3a<T>) source;
        this.tree = localSource.tree;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(T x) {
        assert x != null : "Violation of: x is not null";
        assert !this.contains(x) : "Violation of: x is not in this";

        //Utilizing the method insertInTree will will add 'x' into the binary
        //tree.
        insertInTree(this.tree, x);

    }

    @Override
    public final T remove(T x) {
        assert x != null : "Violation of: x is not null";
        assert this.contains(x) : "Violation of: x is in this";

        //Returning x from the tree and then returning what the method
        //removeFromTree returns.
        return removeFromTree(this.tree, x);
    }

    @Override
    public final T removeAny() {
        assert this.size() > 0 : "Violation of: this /= empty_set";

        //Uses the removeSmallest method on this.tree to remove any T and then
        //returns what removeSmallest returns.
        return removeSmallest(this.tree);
    }

    @Override
    public final boolean contains(T x) {
        assert x != null : "Violation of: x is not null";

        //Using the method isInTree to find which set contains 'x'.
        return isInTree(this.tree, x);
    }

    @Override
    public final int size() {

        //Returns the size of the tree.
        return this.tree.size();
    }

    @Override
    public final Iterator<T> iterator() {
        return this.tree.iterator();
    }

}
