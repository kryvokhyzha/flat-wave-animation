package com.components;

import com.sequential.WaveAnimationSequential;
import com.utils.ImageHelper;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GeneralFrame extends JFrame {
  private ImageHelper imageHelper;
  private JLabel canvasLabel;

  private JButton startBtn;
  private JButton stopBtn;
  private JButton selectFileBtn;

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
    imageHelper = new ImageHelper(String.valueOf(Paths.get("resources/img1.jpg")));
    BufferedImage canvas = imageHelper.getImage();

    canvasLabel = new JLabel(new ImageIcon(canvas));

    JPanel canvasPanel = new JPanel();
    canvasPanel.add(canvasLabel, null);

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Flat wave animation ~ Samsung mini-project");

    canvasPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

    scoreLabel = new ScoreLabel();

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

    JPanel slidersPanel = new JPanel();
    TitledBorder tbf = BorderFactory.createTitledBorder("Parameters");
    tbf.setTitleFont(new Font("Arial", Font.BOLD, 14));
    slidersPanel.setBorder(tbf);

    JLabel delayLabel = new JLabel("Animation delay");
    JSlider delaySlider = new JSlider(0, 1000, delayValue);
    delaySlider.setMajorTickSpacing(250);
    delaySlider.setMinorTickSpacing(100);
    delaySlider.setPaintTicks(true);
    delaySlider.setPaintLabels(true);

    delaySlider.addChangeListener(
        new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            delayValue = ((JSlider) e.getSource()).getValue();
            WaveAnimationSequential.delayValue = delayValue;
          }
        });

    JLabel distortionRadiusLabel = new JLabel("Radius of distortion");
    JSlider distortionRadiusSlider = new JSlider(10, 20, distortionRadiusValue);
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
          }
        });

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
                    .addComponent(slidersPanel)));
  }

  private void startBtnActionPerformed(ActionEvent evt) {
    System.out.println("Start sequential animation.");

    onStartEnable();
    updateCanvas();

    WaveAnimationSequential.delayValue = delayValue;
    WaveAnimationSequential.distortionRadiusValueSquare =
        distortionRadiusValue * distortionRadiusValue;

    WaveAnimationSequential wa =
        new WaveAnimationSequential(this, canvasLabel, imageHelper, scoreLabel);
    WaveAnimationSequential.stopAnimationFlag.set(false);
    wa.start();
  }

  private void stopBtnActionPerformed(ActionEvent evt) {
    System.out.println("Stop sequential animation.");

    WaveAnimationSequential.stopAnimationFlag.set(true);
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

  private void onStartEnable() {
    this.startBtn.setEnabled(false);
    this.stopBtn.setEnabled(true);
    this.selectFileBtn.setEnabled(false);
  }

  private void onStopEnable() {
    this.startBtn.setEnabled(true);
    this.stopBtn.setEnabled(false);
    this.selectFileBtn.setEnabled(true);
  }
}
