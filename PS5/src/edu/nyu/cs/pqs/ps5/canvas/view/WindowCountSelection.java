package edu.nyu.cs.pqs.ps5.canvas.view;

import edu.nyu.cs.pqs.ps5.canvas.impl.AppRules;
import edu.nyu.cs.pqs.ps5.canvas.impl.Model;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 * The WindowCountSelection class consists of the Window Selection GUI which is used to choose the
 * number of pop-up windows to be displayed when the canvas application starts. A user may also
 * optionally select the paint color with which he/she wishes to paint the canvas. This
 * thread-unsafe class implements the Singleton pattern, i.e., only a single instance of this class
 * can be created.
 * 
 * @author Anisha Bhatla
 */
public class WindowCountSelection {

  private final Model model;
  private final JFrame windowSelectionFrame;
  private final JPanel mainPanel;
  private final JLabel numberOfWindowsPrompt;
  private final JSpinner windowCountSpinner;
  private final JLabel colorOfPaintPrompt;
  private final JButton startButton;
  private final JComboBox<String> paintColorComboBox;
  private AppRules.PaintColor paintColorChosen;
  private static WindowCountSelection singletonInstance = null;

  /**
   * Private constructor to prevent instantiation from outside the class
   */
  private WindowCountSelection(Model model) {
    this.model = model;
    windowSelectionFrame = new JFrame("Canvas App");
    mainPanel = new JPanel();
    mainPanel.setLayout(new GridLayout(6, 1, 0, 10));
    numberOfWindowsPrompt = new JLabel(" Please choose number of pop-up windows: ");
    windowCountSpinner = makeDigitsOnlySpinnerUsingDocumentFilter();
    colorOfPaintPrompt = new JLabel(" Please choose paint color (optional): ");
    int index = 0;
    paintColorComboBox = new JComboBox<String>();
    for (AppRules.PaintColor paintColor : AppRules.PaintColor.values()) {
      paintColorComboBox.insertItemAt(paintColor.getColorString(), index++);
    }
    paintColorComboBox.setSelectedIndex(-1);
    startButton = new JButton("Start");
    startButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        onStart();
      }
    });
    mainPanel.add(numberOfWindowsPrompt);
    mainPanel.add(windowCountSpinner);
    mainPanel.add(colorOfPaintPrompt);
    mainPanel.add(paintColorComboBox);
    mainPanel.add(startButton);
    windowSelectionFrame.getContentPane().add(mainPanel);
    windowSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    windowSelectionFrame.pack();
    windowSelectionFrame.setVisible(true);
  }

  /**
   * Creates a new instance of the WindowCountSelection class if none was created earlier, and
   * displays the Window Selection GUI to the user.
   * 
   * @return the instance of WindowCountSelection class
   */
  public static WindowCountSelection getInstanceAndDisplayWindowSelectionPage() {
    if (singletonInstance == null) {
      singletonInstance = new WindowCountSelection(Model.getInstance());
    }
    singletonInstance.windowSelectionFrame.setVisible(true);
    return singletonInstance;
  }

  private JSpinner makeDigitsOnlySpinnerUsingDocumentFilter() {
    JSpinner spinner = new JSpinner(
        new SpinnerNumberModel(1, AppRules.MINIMUM_WINDOWS, AppRules.MAXIMUM_WINDOWS, 1));
    JSpinner.NumberEditor editor = (JSpinner.NumberEditor) spinner.getEditor();
    final Document editorDoc = editor.getTextField().getDocument();
    if (editorDoc instanceof PlainDocument) {
      AbstractDocument doc = new PlainDocument() {

        private static final long serialVersionUID = 1L;

        @Override
        public void setDocumentFilter(DocumentFilter docFilter) {
          if (docFilter instanceof IsIntegerDocumentFilter) {
            super.setDocumentFilter(docFilter);
          }
        }
      };
      doc.setDocumentFilter(new IsIntegerDocumentFilter());
      editor.getTextField().setDocument(doc);
    }
    return spinner;
  }

  private static class IsIntegerDocumentFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
        throws BadLocationException {
      if (IsInteger(string)) {
        super.insertString(fb, offset, string, attr);
      }
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
      super.remove(fb, offset, length);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
        throws BadLocationException {
      if (IsInteger(text)) {
        super.replace(fb, offset, length, text, attrs);
      }
    }

    private boolean IsInteger(String text) {
      for (int i = 0; i < text.length(); i++) {
        if (!Character.isDigit(text.charAt(i))) {
          return false;
        }
      }
      return true;
    }
  }

  private void onStart() {
    String paintColorStringChosen = (String) paintColorComboBox.getSelectedItem();
    if (paintColorStringChosen != null) {
      for (AppRules.PaintColor paintColor : AppRules.PaintColor.values()) {
        if (paintColor.getColorString().equals(paintColorStringChosen)) {
          paintColorChosen = paintColor;
          break;
        }
      }
    }
    windowSelectionFrame.setVisible(false);
    model.startApp(paintColorChosen, (int) windowCountSpinner.getValue());
    paintColorComboBox.setSelectedIndex(-1);
    windowCountSpinner.setValue(1);
    paintColorChosen = null;
  }

  /**
   * For testing purposes only
   */
  Model getModel_forTest() {
    return model;
  }

  /**
   * For testing purposes only
   */
  AppRules.PaintColor getPaintColorChosen_forTest() {
    return paintColorChosen;

  }
}
