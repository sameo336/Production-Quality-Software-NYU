package edu.nyu.cs.pqs.ps5.canvas.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import edu.nyu.cs.pqs.ps5.canvas.impl.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WindowCountSelectionTest {

  private WindowCountSelection windowCountSelectionInstance;
  private WindowCountSelection windowCountSelectionInstanceTwo;
  private Model model;

  @Before
  public void setUp() {
    model = Model.getInstance();
  }

  @After
  public void tearDown() {
    windowCountSelectionInstance = null;
    windowCountSelectionInstanceTwo = null;
    model = null;
  }

  @Test
  public void testGetInstanceAndDisplayWindowSelectionPage() {
    assertNull(windowCountSelectionInstance);
    windowCountSelectionInstance = WindowCountSelection.getInstanceAndDisplayWindowSelectionPage();
    assertNotNull(windowCountSelectionInstance);
    assertNotNull(windowCountSelectionInstance.getModel_forTest());
    assertEquals(model, windowCountSelectionInstance.getModel_forTest());
    assertNull(windowCountSelectionInstance.getPaintColorChosen_forTest());
  }

  @Test
  public void testgetInstanceAndDisplayWindowSelectionPage_multipleCallsToTheMethod() {
    assertNull(windowCountSelectionInstance);
    assertNull(windowCountSelectionInstanceTwo);
    windowCountSelectionInstance = WindowCountSelection.getInstanceAndDisplayWindowSelectionPage();
    assertNotNull(windowCountSelectionInstance);
    assertNotNull(windowCountSelectionInstance.getModel_forTest());
    assertEquals(model, windowCountSelectionInstance.getModel_forTest());
    assertNull(windowCountSelectionInstance.getPaintColorChosen_forTest());
    windowCountSelectionInstanceTwo =
        WindowCountSelection.getInstanceAndDisplayWindowSelectionPage();
    assertNotNull(windowCountSelectionInstanceTwo);
    assertNotNull(windowCountSelectionInstanceTwo.getModel_forTest());
    assertEquals(model, windowCountSelectionInstanceTwo.getModel_forTest());
    assertNull(windowCountSelectionInstanceTwo.getPaintColorChosen_forTest());
    assertEquals(windowCountSelectionInstance, windowCountSelectionInstanceTwo);
  }
}
