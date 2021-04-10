package com.components;

import com.parallel.WaveAnimationParallel;
import com.sequential.WaveAnimationSequential;
import com.utils.ImageHelper;
import com.utils.Mode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GeneralFrame extends JFrame {
  private ImageHelper imageHelper;
  private JScrollPane canvasPanel;
  private JLabel canvasLabel;

  private JButton startBtn;
  private JButton stopBtn;
  private JButton selectFileBtn;

  private JPanel modePanel;
  private JRadioButton radioButton1;
  private JRadioButton radioButton2;
  private Mode mode = Mode.SEQUENTIAL;

  private JPanel slidersPanel;
  private JLabel delayLabel;
  private JSlider delaySlider;
  private JLabel distortionRadiusLabel;
  private JSlider distortionRadiusSlider;

  private int delayValue = 0;
  private int distortionRadiusValue = 10;

  private ScoreLabel scoreLabel;

  public GeneralFrame() {
    initComponents();
  }

  public void updateCanvas() {
    revalidate();
    repaint();
  }

  private void initComponents() {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Flat wave animation ~ Samsung mini-project");

    initCanvasPanel();

    scoreLabel = new ScoreLabel();

    initControlButtons();

    initRadioBtnPanel();

    initSliderPanel();

    initSliderLayout();
    initGeneralLayout();

    pack();
  }

  private void initCanvasPanel() {
    imageHelper = new ImageHelper(String.valueOf(Paths.get("resources/img1.jpg")));

    canvasLabel = new JLabel(new ImageIcon(imageHelper.getImage()));

    JPanel cp = new JPanel();
    cp.add(canvasLabel, null);
    cp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    canvasPanel = new JScrollPane(cp);
  }

  private void initControlButtons() {
    startBtn = new JButton("Start");
    stopBtn = new JButton("Stop");
    selectFileBtn = new JButton("Select image...");

    startBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            startBtnActionPerformed(actionEvent);
          }
        });

    stopBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            stopBtnActionPerformed(actionEvent);
          }
        });
    stopBtn.setEnabled(false);

    selectFileBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            selectFileBtnActionPerformed(actionEvent);
          }
        });
  }

  private void initRadioBtnPanel() {
    modePanel = new JPanel();
    TitledBorder tbf = BorderFactory.createTitledBorder("Mode");
    tbf.setTitleFont(new Font("Arial", Font.BOLD, 14));
    modePanel.setBorder(tbf);
    modePanel.setLayout(new GridLayout(2, 1));

    ButtonGroup buttonGroup = new ButtonGroup();
    radioButton1 = new JRadioButton();
    radioButton2 = new JRadioButton();

    buttonGroup.add(radioButton1);
    radioButton1.setText("Sequential");
    radioButton1.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            radioButton1ActionPerformed(evt);
          }
        });

    buttonGroup.add(radioButton2);
    radioButton2.setText("Parallel");
    radioButton2.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            radioButton2ActionPerformed(evt);
          }
        });

    buttonGroup.setSelected(radioButton1.getModel(), true);

    modePanel.add(radioButton1);
    modePanel.add(radioButton2);
  }

  private void initSliderPanel() {
    slidersPanel = new JPanel();
    TitledBorder tbf = BorderFactory.createTitledBorder("Parameters");
    tbf.setTitleFont(new Font("Arial", Font.BOLD, 14));
    slidersPanel.setBorder(tbf);

    delayLabel = new JLabel("Animation delay");
    delaySlider = new JSlider(0, 1000, delayValue);
    delaySlider.setMajorTickSpacing(250);
    delaySlider.setMinorTickSpacing(100);
    delaySlider.setPaintTicks(true);
    delaySlider.setPaintLabels(true);

    delaySlider.addChangeListener(
        new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            delayValue = ((JSlider) e.getSource()).getValue();
            WaveAnimationSequential.delayValue = delayValue;
            WaveAnimationParallel.delayValue = delayValue;
          }
        });

    distortionRadiusLabel = new JLabel("Radius of distortion");
    distortionRadiusSlider = new JSlider(10, 20, distortionRadiusValue);
    distortionRadiusSlider.setMajorTickSpacing(5);
    distortionRadiusSlider.setMinorTickSpacing(1);
    distortionRadiusSlider.setPaintTicks(true);
    distortionRadiusSlider.setPaintLabels(true);

    distortionRadiusSlider.addChangeListener(
        new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            distortionRadiusValue = ((JSlider) e.getSource()).getValue();
            WaveAnimationSequential.distortionRadiusValueSquare =
                distortionRadiusValue * distortionRadiusValue;
            WaveAnimationParallel.distortionRadiusValueSquare =
                distortionRadiusValue * distortionRadiusValue;
          }
        });
  }

  private void initSliderLayout() {
    GroupLayout slidersPanelLayout = new GroupLayout(slidersPanel);
    slidersPanel.setLayout(slidersPanelLayout);

    slidersPanelLayout.setHorizontalGroup(
        slidersPanelLayout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(
                slidersPanelLayout
                    .createSequentialGroup()
                    .addComponent(delayLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delaySlider))
            .addGroup(
                slidersPanelLayout
                    .createSequentialGroup()
                    .addComponent(
                        distortionRadiusLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(distortionRadiusSlider)));

    slidersPanelLayout.setVerticalGroup(
        slidersPanelLayout
            .createSequentialGroup()
            .addGroup(
                slidersPanelLayout
                    .createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(delayLabel)
                    .addComponent(delaySlider))
            .addGroup(
                slidersPanelLayout
                    .createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(distortionRadiusLabel)
                    .addComponent(distortionRadiusSlider)));
  }

  private void initGeneralLayout() {
    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);

    layout.setHorizontalGroup(
        layout
            .createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addComponent(scoreLabel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addComponent(canvasPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addComponent(startBtn, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(stopBtn, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addComponent(selectFileBtn, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addComponent(modePanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slidersPanel, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    layout.setVerticalGroup(
        layout
            .createSequentialGroup()
            .addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(scoreLabel))
            .addGroup(
                layout
                    .createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(canvasPanel))
            .addGroup(
                layout
                    .createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(startBtn)
                    .addComponent(stopBtn))
            .addGroup(
                layout
                    .createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(selectFileBtn))
            .addGroup(
                layout
                    .createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(modePanel)
                    .addComponent(slidersPanel)));
  }

  private void startBtnActionPerformed(ActionEvent evt) {
    onStartEnable();
    updateCanvas();

    switch (this.mode) {
      case SEQUENTIAL:
        {
          WaveAnimationSequential.delayValue = delayValue;
          WaveAnimationSequential.distortionRadiusValueSquare =
              distortionRadiusValue * distortionRadiusValue;

          WaveAnimationSequential wa =
              new WaveAnimationSequential(this, canvasLabel, imageHelper, scoreLabel);
          WaveAnimationSequential.stopAnimationFlag.set(false);
          wa.start();
          break;
        }

      case PARALLEL:
        WaveAnimationParallel.delayValue = delayValue;
        WaveAnimationParallel.distortionRadiusValueSquare =
            distortionRadiusValue * distortionRadiusValue;

        WaveAnimationParallel wa =
            new WaveAnimationParallel(this, canvasLabel, imageHelper, scoreLabel);
        WaveAnimationParallel.stopAnimationFlag.set(false);
        wa.start();
        break;
    }
  }

  private void stopBtnActionPerformed(ActionEvent evt) {
    switch (this.mode) {
      case SEQUENTIAL:
        WaveAnimationSequential.stopAnimationFlag.set(true);
        break;
      case PARALLEL:
        WaveAnimationParallel.stopAnimationFlag.set(true);
        break;
    }
    updateCanvas();

    onStopEnable();
  }

  private void selectFileBtnActionPerformed(ActionEvent evt) {
    JFileChooser chooser = new JFileChooser();
    chooser.showOpenDialog(null);
    File f = chooser.getSelectedFile();

    imageHelper = new ImageHelper(f.getAbsolutePath());
    canvasLabel.setIcon(new ImageIcon(imageHelper.getImage()));
  }

  private void radioButton1ActionPerformed(ActionEvent evt) {
    this.mode = Mode.SEQUENTIAL;
  }

  private void radioButton2ActionPerformed(ActionEvent evt) {
    this.mode = Mode.PARALLEL;
  }

  private void onStartEnable() {
    this.startBtn.setEnabled(false);
    this.stopBtn.setEnabled(true);
    this.selectFileBtn.setEnabled(false);
    this.radioButton1.setEnabled(false);
    this.radioButton2.setEnabled(false);
  }

  private void onStopEnable() {
    this.startBtn.setEnabled(true);
    this.stopBtn.setEnabled(false);
    this.selectFileBtn.setEnabled(true);
    this.radioButton1.setEnabled(true);
    this.radioButton2.setEnabled(true);
  }
}
