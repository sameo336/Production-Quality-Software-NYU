package edu.nyu.cs.pqs.ps5.canvas.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import edu.nyu.cs.pqs.ps5.canvas.api.Listener;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ModelTest {

  private Model model;
  private Model modelSecondInstance;
  private Model modelThirdInstance;

  @Before
  public void setUp() {
    model = Model.getInstance();
  }

  @After
  public void tearDown() {
    model.setAppStarted_forTest(false);
    model.clearListeners_forTest();
    model = null;
    modelSecondInstance = null;
    modelThirdInstance = null;
  }

  @Rule
  public ExpectedException exceptionThrown = ExpectedException.none();

  @Test
  public void testAddListener() {
    Listener testListener = new Listener() {

      @Override
      public boolean paintingMoveMade(int oldXCoordinate, int oldYCoordinate, int newXCoordinate,
          int newYCoordinate) {
        // do nothing
        return false;

      }

      @Override
      public boolean paintColorChanged(AppRules.PaintColor paintColorChosen) {
        // do nothing
        return false;
      }

      @Override
      public boolean canvasCleared() {
        // do nothing
        return false;
      }

      @Override
      public boolean appRestarted() {
        // do nothing
        return false;
      }

    };

    assertEquals(0, model.getListeners_forTest().size());
    assertFalse(model.getListeners_forTest().contains(testListener));
    assertTrue(model.addListener(testListener));
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
  }

  @Test
  public void testAddListener_nullListener() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Listener cannot be null");
    model.addListener(null);
  }

  @Test
  public void testAddListener_alreadyExistingListener() {
    Listener testListener = new Listener() {

      @Override
      public boolean paintingMoveMade(int oldXCoordinate, int oldYCoordinate, int newXCoordinate,
          int newYCoordinate) {
        // do nothing
        return false;
      }

      @Override
      public boolean paintColorChanged(AppRules.PaintColor paintColorChosen) {
        // do nothing
        return false;
      }

      @Override
      public boolean canvasCleared() {
        // do nothing
        return false;
      }

      @Override
      public boolean appRestarted() {
        // do nothing
        return false;
      }

    };
    assertEquals(0, model.getListeners_forTest().size());
    assertFalse(model.getListeners_forTest().contains(testListener));
    assertTrue(model.addListener(testListener));
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertFalse(model.addListener(testListener));
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
  }

  @Test
  public void testRemoveListener() {
    Listener testListener = new ListenerImplementation.Builder(model).build();
    new ListenerImplementation.Builder(model).build();
    new ListenerImplementation.Builder(model).build();
    assertEquals(3, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertTrue(model.removeListener(testListener));
    assertEquals(2, model.getListeners_forTest().size());
    assertFalse(model.getListeners_forTest().contains(testListener));
  }

  @Test
  public void testRemoveListener_onlyOneListenerInListenerSet() {
    Listener testListener = new ListenerImplementation.Builder(model).build();
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertFalse(model.removeListener(testListener));
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
  }

  @Test
  public void testRemoveListener_nullListener() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Listener cannot be null");
    model.removeListener(null);
  }

  @Test
  public void testRemoveListener_listenerDoesNotExistInListenerSet() {
    new ListenerImplementation.Builder(model).build();
    new ListenerImplementation.Builder(model).build();
    Listener testListener = new Listener() {

      @Override
      public boolean paintingMoveMade(int oldXCoordinate, int oldYCoordinate, int newXCoordinate,
          int newYCoordinate) {
        // do nothing
        return false;
      }

      @Override
      public boolean paintColorChanged(AppRules.PaintColor paintColorChosen) {
        // do nothing
        return false;
      }

      @Override
      public boolean canvasCleared() {
        // do nothing
        return false;
      }

      @Override
      public boolean appRestarted() {
        // do nothing
        return false;
      }

    };
    assertEquals(2, model.getListeners_forTest().size());
    assertFalse(model.getListeners_forTest().contains(testListener));
    assertFalse(model.removeListener(testListener));
    assertEquals(2, model.getListeners_forTest().size());
    assertFalse(model.getListeners_forTest().contains(testListener));
  }

  @Test
  public void testGetInstance() {
    assertNull(modelSecondInstance);
    modelSecondInstance = Model.getInstance();
    assertNotNull(modelSecondInstance);
    assertNotNull(modelSecondInstance.getListeners_forTest());
    assertEquals(0, modelSecondInstance.getListeners_forTest().size());
    assertFalse(modelSecondInstance.getAppStarted_forTest());
  }

  @Test
  public void testGetInstance_multipleCallsToTheMethod() {
    assertNull(modelSecondInstance);
    assertNull(modelThirdInstance);
    modelSecondInstance = Model.getInstance();
    assertNotNull(modelSecondInstance);
    assertNotNull(modelSecondInstance.getListeners_forTest());
    assertEquals(0, modelSecondInstance.getListeners_forTest().size());
    assertFalse(modelSecondInstance.getAppStarted_forTest());
    modelThirdInstance = Model.getInstance();
    assertNotNull(modelThirdInstance);
    assertNotNull(modelThirdInstance.getListeners_forTest());
    assertEquals(0, modelThirdInstance.getListeners_forTest().size());
    assertFalse(modelThirdInstance.getAppStarted_forTest());
    assertEquals(modelSecondInstance, modelThirdInstance);
  }

  @Test
  public void testStartApp_numberOfWindowsLessThanMinimumPossible() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Invalid Number of Windows");
    model.startApp(AppRules.PaintColor.BLACK, AppRules.MINIMUM_WINDOWS - 1);
  }

  @Test
  public void testStartApp_numberOfWindowsGreaterThanMaximumPossible() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Invalid Number of Windows");
    model.startApp(AppRules.PaintColor.BLACK, AppRules.MAXIMUM_WINDOWS + 1);
  }

  @Test
  public void testStartApp_appAlreadyStarted() {
    model.setAppStarted_forTest(true);
    assertTrue(model.getAppStarted_forTest());
    exceptionThrown.expect(IllegalStateException.class);
    exceptionThrown.expectMessage("App has already started");
    model.startApp(AppRules.PaintColor.BLACK, 1);
  }

  @Test
  public void testStartApp_nonNullPaintColorAndSinglePopUpWindowAsParameters() {
    model.setAppStarted_forTest(false);
    assertFalse(model.getAppStarted_forTest());
    assertEquals(0, model.getListeners_forTest().size());
    assertTrue(model.startApp(AppRules.PaintColor.BLUE, 1));
    assertTrue(model.getAppStarted_forTest());
    assertEquals(1, model.getListeners_forTest().size());
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.PaintColor.BLUE, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.PaintColor.BLUE,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
  }

  @Test
  public void testStartApp_nonNullPaintColorAndMultiplePopUpWindowsAsParameters() {
    model.setAppStarted_forTest(false);
    assertFalse(model.getAppStarted_forTest());
    assertEquals(0, model.getListeners_forTest().size());
    assertTrue(model.startApp(AppRules.PaintColor.BLUE, 2));
    assertTrue(model.getAppStarted_forTest());
    assertEquals(2, model.getListeners_forTest().size());
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.PaintColor.BLUE, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.PaintColor.BLUE,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
  }

  @Test
  public void testStartApp_nullPaintColorAndSinglePopUpWindowAsParameters() {
    model.setAppStarted_forTest(false);
    assertFalse(model.getAppStarted_forTest());
    assertEquals(0, model.getListeners_forTest().size());
    assertTrue(model.startApp(null, 1));
    assertTrue(model.getAppStarted_forTest());
    assertEquals(1, model.getListeners_forTest().size());
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.DEFAULT_COLOR, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.DEFAULT_COLOR,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
  }

  @Test
  public void testStartApp_nullPaintColorAndMultiplePopUpWindowsAsParameters() {
    model.setAppStarted_forTest(false);
    assertFalse(model.getAppStarted_forTest());
    assertEquals(0, model.getListeners_forTest().size());
    assertTrue(model.startApp(null, 3));
    assertTrue(model.getAppStarted_forTest());
    assertEquals(3, model.getListeners_forTest().size());
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.DEFAULT_COLOR, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.DEFAULT_COLOR,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
  }

  @Test
  public void testChangePaintColor_nullColor() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("New Paint Color cannot be null");
    model.changePaintColor(null);
  }

  @Test
  public void testChangePaintColor_appNotYetStarted() {
    assertFalse(model.getAppStarted_forTest());
    exceptionThrown.expect(IllegalStateException.class);
    exceptionThrown.expectMessage("App has not yet started");
    model.changePaintColor(AppRules.PaintColor.BLUE);
  }

  @Test
  public void testChangePaintColor() {
    Listener testListener =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.RED).build();
    Listener testListenerTwo =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.RED).build();
    assertEquals(2, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertTrue(model.getListeners_forTest().contains(testListenerTwo));
    model.setAppStarted_forTest(true);
    assertTrue(model.getAppStarted_forTest());
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.PaintColor.RED, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.PaintColor.RED,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
    assertTrue(model.changePaintColor(AppRules.PaintColor.GREEN));
    assertTrue(model.getAppStarted_forTest());
    assertEquals(2, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertTrue(model.getListeners_forTest().contains(testListenerTwo));
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.PaintColor.GREEN, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.PaintColor.GREEN,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
  }

  @Test
  public void testChangePaintColor_samePaintColorAsBefore() {
    Listener testListener =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.RED).build();
    Listener testListenerTwo =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.RED).build();
    assertEquals(2, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertTrue(model.getListeners_forTest().contains(testListenerTwo));
    model.setAppStarted_forTest(true);
    assertTrue(model.getAppStarted_forTest());
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.PaintColor.RED, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.PaintColor.RED,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
    assertTrue(model.changePaintColor(AppRules.PaintColor.RED));
    assertTrue(model.getAppStarted_forTest());
    assertEquals(2, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertTrue(model.getListeners_forTest().contains(testListenerTwo));
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.PaintColor.RED, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.PaintColor.RED,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
  }

  @Test
  public void testClearCanvas_appNotYetStarted() {
    assertFalse(model.getAppStarted_forTest());
    exceptionThrown.expect(IllegalStateException.class);
    exceptionThrown.expectMessage("App has not yet started");
    model.clearCanvas();
  }

  @Test
  public void testClearCanvas() {
    Listener testListener =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.YELLOW).build();
    Listener testListenerTwo =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.YELLOW).build();
    assertEquals(2, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertTrue(model.getListeners_forTest().contains(testListenerTwo));
    model.setAppStarted_forTest(true);
    assertTrue(model.getAppStarted_forTest());
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.PaintColor.YELLOW, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.PaintColor.YELLOW,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
    assertTrue(model.clearCanvas());
    assertTrue(model.getAppStarted_forTest());
    assertEquals(2, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertTrue(model.getListeners_forTest().contains(testListenerTwo));
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.PaintColor.YELLOW, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.PaintColor.YELLOW,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
  }

  @Test
  public void testMakePaintingMove_appNotYetStarted() {
    assertFalse(model.getAppStarted_forTest());
    exceptionThrown.expect(IllegalStateException.class);
    exceptionThrown.expectMessage("App has not yet started");
    model.makePaintingMove(0, 0, 1, 1);
  }

  @Test
  public void testMakePaintingMove() {
    Listener testListener =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.BLUE).build();
    Listener testListenerTwo =
        new ListenerImplementation.Builder(model).color(AppRules.PaintColor.BLUE).build();
    assertEquals(2, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertTrue(model.getListeners_forTest().contains(testListenerTwo));
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.PaintColor.BLUE, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.PaintColor.BLUE,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
    model.setAppStarted_forTest(true);
    assertTrue(model.getAppStarted_forTest());
    assertTrue(model.makePaintingMove(0, 0, 1, 1));
    assertTrue(model.getAppStarted_forTest());
    assertEquals(2, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertTrue(model.getListeners_forTest().contains(testListenerTwo));
    for (Listener listener : model.getListeners_forTest()) {
      if (listener instanceof ListenerImplementation) {
        ListenerImplementation listenerInstance = (ListenerImplementation) listener;
        assertNotNull(listenerInstance);
        assertNotNull(listenerInstance.getModel_forTest());
        assertEquals(model, listenerInstance.getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest());
        assertNotNull(listenerInstance.getCurrentPaintColor_forTest());
        assertEquals(AppRules.PaintColor.BLUE, listenerInstance.getCurrentPaintColor_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertEquals(model, listenerInstance.getAppWindowInstance_forTest().getModel_forTest());
        assertNotNull(listenerInstance.getAppWindowInstance_forTest().getBoard_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertEquals(AppRules.PaintColor.BLUE,
            listenerInstance.getAppWindowInstance_forTest().getBoardDefaultColor_forTest());
        assertNotNull(
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(listenerInstance,
            listenerInstance.getAppWindowInstance_forTest().getListenerInstance_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldXCoordinate_forTest());
        assertEquals(0,
            listenerInstance.getAppWindowInstance_forTest().getOldYCoordinate_forTest());
        assertEquals(1,
            listenerInstance.getAppWindowInstance_forTest().getNewXCoordinate_forTest());
        assertEquals(1,
            listenerInstance.getAppWindowInstance_forTest().getNewYCoordinate_forTest());
      }
    }
  }

  @Test
  public void testRestartApp_appHasAlreadyStarted() {
    Listener testListener = new ListenerImplementation.Builder(model).build();
    model.setAppStarted_forTest(true);
    assertTrue(model.getAppStarted_forTest());
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertTrue(model.restartApp());
    assertFalse(model.getAppStarted_forTest());
    assertEquals(0, model.getListeners_forTest().size());
    assertFalse(model.getListeners_forTest().contains(testListener));

  }

  @Test
  public void testRestartApp_appNotYetStarted() {
    Listener testListener = new ListenerImplementation.Builder(model).build();
    assertFalse(model.getAppStarted_forTest());
    assertEquals(1, model.getListeners_forTest().size());
    assertTrue(model.getListeners_forTest().contains(testListener));
    assertTrue(model.restartApp());
    assertFalse(model.getAppStarted_forTest());
    assertEquals(0, model.getListeners_forTest().size());
    assertFalse(model.getListeners_forTest().contains(testListener));
  }

  @Test
  public void testRestartApp_withZeroListenersAndAppAlreadyStarted() {
    model.setAppStarted_forTest(true);
    assertTrue(model.getAppStarted_forTest());
    assertEquals(0, model.getListeners_forTest().size());
    assertTrue(model.restartApp());
    assertFalse(model.getAppStarted_forTest());
    assertEquals(0, model.getListeners_forTest().size());
  }

  @Test
  public void testRestartApp_withZeroListenersAndAppNotYetStarted() {
    assertFalse(model.getAppStarted_forTest());
    assertEquals(0, model.getListeners_forTest().size());
    assertTrue(model.restartApp());
    assertFalse(model.getAppStarted_forTest());
    assertEquals(0, model.getListeners_forTest().size());
  }
}
