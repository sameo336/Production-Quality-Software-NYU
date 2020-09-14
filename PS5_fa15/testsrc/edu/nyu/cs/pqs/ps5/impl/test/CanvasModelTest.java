package edu.nyu.cs.pqs.ps5.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import edu.nyu.cs.pqs.ps5.api.CanvasModel;
import edu.nyu.cs.pqs.ps5.impl.CanvasModelImpl;
import edu.nyu.cs.pqs.ps5.impl.WindowImpl;

public class CanvasModelTest {
  private CanvasModelImpl model1;

  @Before
  public void initialize() {
    model1 = CanvasModelImpl.getInstance();
  }

  /**
   * Testing singleton constructor. Two returned instances should be same.
   */
  @Test
  public void testGetInstance() {
    CanvasModel model1 = CanvasModelImpl.getInstance();
    CanvasModel model2 = CanvasModelImpl.getInstance();
    assertEquals(model1, model2);
  }

  /**
   * This test case tests the addobserver method, the notifymove method and the
   * updateDrawingBoard method.
   */
  @Test
  public void testNotifyModel() {
    WindowImpl w1 = new WindowImpl(model1);
    WindowImpl w2 = new WindowImpl(model1);
    model1.addObserver(w1);
    model1.addObserver(w2);
    model1.notifyMove(1, 2, 10, 20);
    assertTrue(model1.getObserverList().size() == 2);
    assertTrue(w1.getOldX() == 1 && w1.getOldY() == 2 && w1.getCurrentX() == 10
        && w1.getCurrentY() == 20);
    assertTrue(w2.getOldX() == 1 && w2.getOldY() == 2 && w2.getCurrentX() == 10
        && w2.getCurrentY() == 20);
  }

}
