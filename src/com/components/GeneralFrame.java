package com.components;

import com.animation.WaveAnimation;
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
  private JLabel speedLabel;
  private JSlider speedSlider;
  private JLabel delayLabel;
  private JSlider delaySlider;
  private JLabel distortionRadiusLabel;
  private JSlider distortionRadiusSlider;

  private int speedValue = 6;
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

    initModePanel();
    initModeLayout();

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

  private void initModePanel() {
    modePanel = new JPanel();
    TitledBorder tbf = BorderFactory.createTitledBorder("Mode");
    tbf.setTitleFont(new Font("Arial", Font.BOLD, 14));
    modePanel.setBorder(tbf);

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
  }

  private void initSliderPanel() {
    slidersPanel = new JPanel();
    TitledBorder tbf = BorderFactory.createTitledBorder("Parameters");
    tbf.setTitleFont(new Font("Arial", Font.BOLD, 14));
    slidersPanel.setBorder(tbf);

    speedLabel = new JLabel("Speed of propagation");
    speedSlider = new JSlider(1, 50, speedValue);
    speedSlider.setMajorTickSpacing(10);
    speedSlider.setMinorTickSpacing(5);
    speedSlider.setPaintTicks(true);
    // speedSlider.setPaintLabels(true);

    speedSlider.addChangeListener(
        new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            speedValue = ((JSlider) e.getSource()).getValue();
            WaveAnimation.speedValue = speedValue;
          }
        });

    delayLabel = new JLabel("Animation delay");
    delaySlider = new JSlider(0, 250, delayValue);
    delaySlider.setMajorTickSpacing(50);
    delaySlider.setMinorTickSpacing(25);
    delaySlider.setPaintTicks(true);
    // delaySlider.setPaintLabels(true);

    delaySlider.addChangeListener(
        new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            delayValue = ((JSlider) e.getSource()).getValue();
            WaveAnimation.delayValue = delayValue;
          }
        });

    distortionRadiusLabel = new JLabel("Radius of distortion");
    distortionRadiusSlider = new JSlider(10, 20, distortionRadiusValue);
    distortionRadiusSlider.setMajorTickSpacing(5);
    distortionRadiusSlider.setMinorTickSpacing(1);
    distortionRadiusSlider.setPaintTicks(true);
    // distortionRadiusSlider.setPaintLabels(true);

    distortionRadiusSlider.addChangeListener(
        new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            distortionRadiusValue = ((JSlider) e.getSource()).getValue();
            WaveAnimation.distortionRadiusValueSquare = distortionRadiusValue;
            WaveAnimation.distortionRadiusValueSquare =
                distortionRadiusValue * distortionRadiusValue;
          }
        });
  }

  private void initModeLayout() {
    GroupLayout modePanelLayout = new GroupLayout(modePanel);
    modePanel.setLayout(modePanelLayout);

    GroupLayout.SequentialGroup hGroup = modePanelLayout.createSequentialGroup();

    hGroup.addGroup(
        modePanelLayout
            .createParallelGroup()
            .addComponent(radioButton1)
            .addComponent(radioButton2));

    modePanelLayout.setHorizontalGroup(hGroup);

    GroupLayout.SequentialGroup vGroup = modePanelLayout.createSequentialGroup();
    vGroup.addGroup(
        modePanelLayout
            .createSequentialGroup()
            .addComponent(radioButton1)
            .addComponent(radioButton2));
    modePanelLayout.setVerticalGroup(vGroup);
  }

  private void initSliderLayout() {
    GroupLayout slidersPanelLayout = new GroupLayout(slidersPanel);
    slidersPanel.setLayout(slidersPanelLayout);

    GroupLayout.SequentialGroup hGroup = slidersPanelLayout.createSequentialGroup();

    hGroup
        .addGroup(
            slidersPanelLayout
                .createParallelGroup()
                .addComponent(speedLabel)
                .addComponent(delayLabel)
                .addComponent(distortionRadiusLabel))
        .addGroup(
            slidersPanelLayout
                .createParallelGroup()
                .addComponent(speedSlider)
                .addComponent(delaySlider)
                .addComponent(distortionRadiusSlider));

    slidersPanelLayout.setHorizontalGroup(hGroup);

    GroupLayout.SequentialGroup vGroup = slidersPanelLayout.createSequentialGroup();
    vGroup
        .addGroup(
            slidersPanelLayout
                .createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(speedLabel)
                .addComponent(speedSlider))
        .addGroup(
            slidersPanelLayout
                .createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(delayLabel)
                .addComponent(delaySlider))
        .addGroup(
            slidersPanelLayout
                .createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(distortionRadiusLabel)
                .addComponent(distortionRadiusSlider));
    slidersPanelLayout.setVerticalGroup(vGroup);
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

    WaveAnimation.speedValue = speedValue;
    WaveAnimation.delayValue = delayValue;
    WaveAnimation.distortionRadiusValue = distortionRadiusValue;
    WaveAnimation.distortionRadiusValueSquare = distortionRadiusValue * distortionRadiusValue;

    WaveAnimation wa = new WaveAnimation(this, canvasLabel, imageHelper, scoreLabel, this.mode);
    WaveAnimation.stopAnimationFlag.set(false);
    wa.start();
  }

  private void stopBtnActionPerformed(ActionEvent evt) {
    WaveAnimation.stopAnimationFlag.set(true);
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
