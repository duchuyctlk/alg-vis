package algvis;

import static algvis.helper.ReflectionHelper.createObject;
import static algvis.helper.ReflectionHelper.getFieldValue;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import algvis.core.Algorithm;
import algvis.internationalization.IButton;
import algvis.ui.AlgVis;
import algvis.ui.Buttons;
import algvis.ui.VisPanel;

public class BaseTest {
    protected static final long buttonClickSleepTime = 500;

    protected static JFrame showMainFrame() {
        Object obj = createObject("algvis.ui.MainFrame");

        JFrame mainFrame = null;
        if (obj != null) {
            mainFrame = (JFrame) obj;
            mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
            mainFrame.paint(mainFrame.getGraphics());
        }

        return mainFrame;
    }

    protected static AlgVis getAlgVis(JFrame mainFrame) {
        Object obj = getFieldValue(mainFrame, "A");
        return obj != null ? (AlgVis) obj : null;
    }

    protected VisPanel getActiveVisPanel(AlgVis algVis) {
        if (algVis == null || algVis.panels == null) {
            return null;
        }

        int activePanelIndex = (Integer) getFieldValue(algVis, "activePanel");
        if (activePanelIndex < 0 || activePanelIndex >= algVis.panels.length) {
            return null;
        }

        return algVis.panels[activePanelIndex];
    }

    private Buttons getVisPanelButtons(VisPanel visPanel) {
        return visPanel == null ? null : visPanel.buttons;
    }

    private IButton getButton(VisPanel visPanel, String buttonName) {
        if (visPanel == null || buttonName == null || buttonName.equals("")) {
            return null;
        }
        Buttons buttons = getVisPanelButtons(visPanel);
        return (IButton) getFieldValue(buttons, buttonName);
    }

    private void clickButton(VisPanel visPanel, String buttonName) {
        IButton button = getButton(visPanel, buttonName);

        if (button != null) {
            button.doClick();
        }
    }

    protected void clearVisPanel(VisPanel visPanel) {
        Exception ex = null;
        try {
            clickButton(visPanel, "clear");
        } catch (Exception e) {
            ex = e;
            System.out.println(e.getMessage());
        }

        if (ex != null) {
            try {
                final VisPanel panel = visPanel;
                Algorithm clearVisPanelAlg = new Algorithm(panel) {

                    @Override
                    public void runAlgorithm() {
                        panel.D.clear();
                    }
                };
                panel.D.start(clearVisPanelAlg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
