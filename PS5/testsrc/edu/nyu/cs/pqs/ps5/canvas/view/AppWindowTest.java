package edu.nyu.cs.pqs.ps5.canvas.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import edu.nyu.cs.pqs.ps5.canvas.api.Listener;
import edu.nyu.cs.pqs.ps5.canvas.impl.AppRules;
import edu.nyu.cs.pqs.ps5.canvas.impl.Model;
import org.junit.Before;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AppWindowTest {

  private AppWindow appWindowInstance;
  private Model model;
  private Listener listenerInstance;

  @Before
  public void setUp() {
    model = Model.getInstance();
    listenerInstance = new Listener() {

      @Override
      public boolean appRestarted() {
        // do nothing
        return false;
      }

      @Override
      public boolean paintingMoveMade(int oldXCoordinate, int oldYCoordinate, int newXCoordinate,
          int newYCoordinate) {
        // do nothing
        return false;
      }

      @Override
      public boolean paintColorChanged(AppRules.PaintColor newPaintColorChosen) {
        // do nothing
        return false;
      }

      @Override
      public boolean canvasCleared() {
        // do nothing
        return false;
      }
    };
  }

  @After
  public void tearDown() {
    model = null;
    appWindowInstance = null;
    listenerInstance = null;
  }

  @Rule
  public ExpectedException exceptionThrown = ExpectedException.none();

  @Test
  public void testConstructor_nullModel() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Model instance cannot be null");
    appWindowInstance = new AppWindow(null, AppRules.PaintColor.BLUE, listenerInstance);
  }

  @Test
  public void testConstructor_nullPaintColor() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Paint Color cannot be null");
    appWindowInstance = new AppWindow(model, null, listenerInstance);
  }

  @Test
  public void testConstructor_nullListener() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Listener cannot be null");
    appWindowInstance = new AppWindow(model, AppRules.PaintColor.RED, null);
  }

  @Test
  public void testConstructor() {
    assertNull(appWindowInstance);
    appWindowInstance = new AppWindow(model, AppRules.PaintColor.BLUE, listenerInstance);
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.BLUE, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(0, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewYCoordinate_forTest());
  }

  @Test
  public void testOnPaintColorChanged_nullPaintColor() {
    assertNull(appWindowInstance);
    appWindowInstance = new AppWindow(model, AppRules.PaintColor.BLUE, listenerInstance);
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.BLUE, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(0, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewYCoordinate_forTest());
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Paint Color cannot be null");
    appWindowInstance.onPaintColorChanged(null);
  }

  @Test
  public void testOnPaintColorChanged_nonNullPaintColor() {
    assertNull(appWindowInstance);
    appWindowInstance = new AppWindow(model, AppRules.PaintColor.BLUE, listenerInstance);
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.BLUE, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(0, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewYCoordinate_forTest());
    assertTrue(appWindowInstance.onPaintColorChanged(AppRules.PaintColor.CYAN));
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.CYAN, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(0, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewYCoordinate_forTest());
  }

  @Test
  public void testOnPaintColorChanged_samePaintColorAsBefore() {
    assertNull(appWindowInstance);
    appWindowInstance = new AppWindow(model, AppRules.PaintColor.YELLOW, listenerInstance);
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.YELLOW, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(0, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewYCoordinate_forTest());
    assertFalse(appWindowInstance.onPaintColorChanged(AppRules.PaintColor.YELLOW));
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.YELLOW, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(0, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewYCoordinate_forTest());
  }

  @Test
  public void testOnCanvasCleared() {
    assertNull(appWindowInstance);
    appWindowInstance = new AppWindow(model, AppRules.PaintColor.CYAN, listenerInstance);
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.CYAN, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(0, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewYCoordinate_forTest());
    assertTrue(appWindowInstance.onCanvasCleared());
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.CYAN, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(0, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewYCoordinate_forTest());
  }

  @Test
  public void testOnPaintingMoveMade() {
    assertNull(appWindowInstance);
    appWindowInstance = new AppWindow(model, AppRules.PaintColor.YELLOW, listenerInstance);
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.YELLOW, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(0, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewYCoordinate_forTest());
    appWindowInstance.setOldXCoordinate_forTest(2);
    appWindowInstance.setOldYCoordinate_forTest(4);
    assertEquals(2, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(4, appWindowInstance.getOldYCoordinate_forTest());
    assertTrue(appWindowInstance.onPaintingMoveMade(appWindowInstance.getOldXCoordinate_forTest(),
        appWindowInstance.getOldYCoordinate_forTest(), 5, 10));
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.YELLOW, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(2, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(4, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(5, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(10, appWindowInstance.getNewYCoordinate_forTest());
  }

  @Test
  public void testOnAppRestarted() {
    assertNull(appWindowInstance);
    appWindowInstance = new AppWindow(model, AppRules.PaintColor.RED, listenerInstance);
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.RED, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(0, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewYCoordinate_forTest());
    assertTrue(appWindowInstance.onAppRestarted());
    assertNotNull(appWindowInstance);
    assertNotNull(appWindowInstance.getModel_forTest());
    assertNotNull(appWindowInstance.getBoard_forTest());
    assertNotNull(appWindowInstance.getListenerInstance_forTest());
    assertNotNull(appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(model, appWindowInstance.getModel_forTest());
    assertEquals(AppRules.PaintColor.RED, appWindowInstance.getBoardDefaultColor_forTest());
    assertEquals(listenerInstance, appWindowInstance.getListenerInstance_forTest());
    assertEquals(0, appWindowInstance.getOldXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getOldYCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewXCoordinate_forTest());
    assertEquals(0, appWindowInstance.getNewYCoordinate_forTest());

  }
}
