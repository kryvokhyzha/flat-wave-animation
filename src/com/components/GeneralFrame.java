package com.components;

import com.sequential.StopAnimationFlag;
import com.sequential.WaveAnimation;
import com.utils.ImageHelper;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import javax.swing.*;
import javax.swing.border.BevelBorder;

public class GeneralFrame extends JFrame {
  private ImageHelper imageHelper;
  private BufferedImage canvas;
  private JLabel canvasLabel;

  private JButton jButton1;
  private JButton jButton2;
  private ScoreLabel scoreLabel;
  private JPanel jPanel1;

  private WaveAnimation wa;

  public GeneralFrame() {
    initComponents();
  }

  public void updateCanvas() {
    revalidate();
    repaint();
  }

  private void initComponents() {
    imageHelper = new ImageHelper(String.valueOf(Paths.get("resources/img1.jpg")));
    canvas = imageHelper.getImage();

    canvasLabel = new JLabel(new ImageIcon(canvas));

    jPanel1 = new JPanel();
    jPanel1.add(canvasLabel, null);

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Flat wave animation ~ Samsung mini-project");

    // jPanel1.setBackground(new Color(255, 255, 255));
    jPanel1.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

    scoreLabel = new ScoreLabel();

    jButton1 = new JButton("Start");
    jButton2 = new JButton("Stop");

    jButton1.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            jButton1ActionPerformed(actionEvent);
          }
        });

    jButton2.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
            jButton2ActionPerformed(actionEvent);
          }
        });

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
                    .addComponent(jPanel1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(
                layout
                    .createSequentialGroup()
                    .addComponent(jButton1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

    layout.setVerticalGroup(
        layout
            .createSequentialGroup()
            .addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(scoreLabel))
            .addGroup(
                layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jPanel1))
            .addGroup(
                layout
                    .createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)));
  }

  private void jButton1ActionPerformed(ActionEvent evt) {
    System.out.println("Start sequential animation.");
    wa = new WaveAnimation(this, canvasLabel, imageHelper, scoreLabel);
    StopAnimationFlag.stopAnimationFlag.set(false);
    wa.start();
  }

  private void jButton2ActionPerformed(ActionEvent evt) {
    System.out.println("Stop sequential animation.");
    StopAnimationFlag.stopAnimationFlag.set(true);
    updateCanvas();
  }
}
