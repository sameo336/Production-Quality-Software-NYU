package edu.nyu.cs.pqs.ps5.canvas.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import edu.nyu.cs.pqs.ps5.canvas.view.AppWindow;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ListenerImplementationTest {

  private ListenerImplementation listenerInstance;
  private Model model;

  @Before
  public void setUp() {
    model = Model.getInstance();
  }

  @After
  public void tearDown() {
    model.clearListeners_forTest();
    listenerInstance = null;
    model = null;
  }

  @Rule
  public ExpectedException exceptionThrown = ExpectedException.none();

  @Test
  public void testConstructor_withNullModel() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Model instance cannot be null");
    listenerInstance = new ListenerImplementation.Builder(null).build();
  }

  @Test
  public void testConstructor_withNonNullModel() {
    assertNull(listenerInstance);
    assertEquals(0, model.getListeners_forTest().size());
    listenerInstance = new ListenerImplementation.Builder(model).build();
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest());
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.DEFAULT_COLOR, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.DEFAULT_COLOR,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
  }

  @Test
  public void testConstructor_withNonNullModelAndNullColor() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Paint Color cannot be null");
    listenerInstance = new ListenerImplementation.Builder(model).color(null).build();
  }

  @Test
  public void testConstructor_withNonNullModelAndNonNullColor() {
    assertNull(listenerInstance);
    assertEquals(0, model.getListeners_forTest().size());
    listenerInstance =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.YELLOW).build();
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest());
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.PaintColor.YELLOW, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.PaintColor.YELLOW,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
  }

  @Test
  public void testPaintColorChanged_nullPaintColor() {
    assertNull(listenerInstance);
    assertEquals(0, model.getListeners_forTest().size());
    listenerInstance = new ListenerImplementation.Builder(model).build();
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest());
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.DEFAULT_COLOR, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.DEFAULT_COLOR,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Paint Color cannot be null");
    listenerInstance.paintColorChanged(null);
  }

  @Test
  public void testPaintColorChanged_nonNullPaintColor() {
    assertNull(listenerInstance);
    assertEquals(0, model.getListeners_forTest().size());
    listenerInstance =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.CYAN).build();
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest());
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.PaintColor.CYAN, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.PaintColor.CYAN,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    AppWindow appWindowInstance = listenerInstance.getAppWindowInstance_forTest();
    assertNotNull(appWindowInstance);
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
    assertTrue(listenerInstance.paintColorChanged(AppRules.PaintColor.GREEN));
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest());
    assertEquals(appWindowInstance, listenerInstance.getAppWindowInstance_forTest());
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.PaintColor.GREEN, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.PaintColor.GREEN,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
  }

  @Test
  public void testPaintColorChanged_samePaintColorAsBefore() {
    assertNull(listenerInstance);
    assertEquals(0, model.getListeners_forTest().size());
    listenerInstance =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.BLUE).build();
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    AppWindow appWindowInstance = listenerInstance.getAppWindowInstance_forTest();
    assertNotNull(appWindowInstance);
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.PaintColor.BLUE, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.PaintColor.BLUE,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
    assertFalse(listenerInstance.paintColorChanged(AppRules.PaintColor.BLUE));
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest());
    assertEquals(appWindowInstance, listenerInstance.getAppWindowInstance_forTest());
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.PaintColor.BLUE, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.PaintColor.BLUE,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
  }

  @Test
  public void testCanvasCleared() {
    assertNull(listenerInstance);
    assertEquals(0, model.getListeners_forTest().size());
    listenerInstance =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.RED).build();
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    AppWindow appWindowInstance = listenerInstance.getAppWindowInstance_forTest();
    assertNotNull(appWindowInstance);
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.PaintColor.RED, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.PaintColor.RED,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
    assertTrue(listenerInstance.canvasCleared());
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest());
    assertEquals(appWindowInstance, listenerInstance.getAppWindowInstance_forTest());
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.PaintColor.RED, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.PaintColor.RED,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
  }

  @Test
  public void testPaintingMoveMade() {
    assertNull(listenerInstance);
    assertEquals(0, model.getListeners_forTest().size());
    listenerInstance =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.RED).build();
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    AppWindow appWindowInstance = listenerInstance.getAppWindowInstance_forTest();
    assertNotNull(appWindowInstance);
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.PaintColor.RED, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.PaintColor.RED,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
    assertTrue(listenerInstance.paintingMoveMade(0, 0, 2, 3));
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest());
    assertEquals(appWindowInstance, listenerInstance.getAppWindowInstance_forTest());
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.PaintColor.RED, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.PaintColor.RED,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(2, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(3, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
  }

  @Test
  public void testAppRestarted() {
    assertNull(listenerInstance);
    assertEquals(0, model.getListeners_forTest().size());
    listenerInstance =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.BLUE).build();
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    AppWindow appWindowInstance = listenerInstance.getAppWindowInstance_forTest();
    assertNotNull(appWindowInstance);
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.PaintColor.BLUE, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.PaintColor.BLUE,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
    assertTrue(listenerInstance.appRestarted());
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(listenerInstance));
    assertNotNull(listenerInstance);
    assertNotNull(listenerInstance.getModel_forTest());
    assertEquals(model, listenerInstance.getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest());
    assertEquals(appWindowInstance, listenerInstance.getAppWindowInstance_forTest());
    assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
    assertEquals(AppRules.PaintColor.BLUE, listenerInstance.getCurrentPaintColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertEquals(AppRules.PaintColor.BLUE,
        listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
    assertNotNull(listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(listenerInstance,
        listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
    assertEquals(0, listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
  }
}
