/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Database.Database;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author kn
 */
public class ButtonEvent extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private int row;
    private int col;
    private int bound = 2;
    private int size = 50;
    private int score = 0;
    private JButton[][] btn;
    private Point p1 = null;
    private Point p2 = null;
    private Controller algorithm;
    private PointLine line;
    private MainFrame frame;
    private Color backGroundColor = Color.lightGray;
    private int item;
    private int[][] save;
    private String db_user = "kn";
    private String db_pass = "1";
    
    
    
    Music m = new Music();
    AudioStream as = null;
    AudioPlayer ap = AudioPlayer.player;

    public ButtonEvent(MainFrame frame, int row, int col) {
        this.frame = frame;
        this.row = row + 2;
        this.col = col + 2;
        item = row * col / 2;

        setLayout(new GridLayout(row, col, bound, bound));
        setBackground(backGroundColor);
        setPreferredSize(new Dimension((size + bound) * col, (size + bound)
                * row));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setAlignmentY(JPanel.CENTER_ALIGNMENT);

        newGame();

    }
    
    public ButtonEvent(MainFrame frame, int row, int col, int[][] matrix) {
        this.frame = frame;
        this.row = row + 2;
        this.col = col + 2;
        item = row * col / 2;

        setLayout(new GridLayout(row, col, bound, bound));
        setBackground(backGroundColor);
        setPreferredSize(new Dimension((size + bound) * col, (size + bound)
                * row));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setAlignmentY(JPanel.CENTER_ALIGNMENT);

        loadExistGame();

    }

    public void newGame() {
        algorithm = new Controller(this.frame, this.row, this.col);
        addArrayButton();

    }
    
    public void loadExistGame() {
        algorithm = new Controller(this.frame, this.row, this.col);
//        score = Window.score;
//        frame.lbScore.setText(score + "");
        continueArrayButton(Window.matrix);
//        for (int i = 0; i < 10; i ++) {
//            for (int j = 0; j < 10; j++) {
//                System.out.println(Window.matrix[i][j]);
//            }
//        }
    }
    
    public void saveGame(int time) {
        Database db = new Database();
        File f_userid = new File("ID_User.txt");
        String data = "";
        String signal = "";
        try {
            Scanner reader = new Scanner(f_userid);
            data = reader.nextLine();
            signal = reader.nextLine();
            System.out.println("reader: " + data);
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(ButtonEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        db.CheckConn(db_user, db_pass);
        System.out.println("now it save");
        save = algorithm.getMatrix();
        db.SavingGame(save, time, score, Integer.parseInt(data), signal);
    }

    private void addArrayButton() {
        btn = new JButton[row][col];
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                btn[i][j] = createButton(i + "," + j);
                Icon icon = getIcon(algorithm.getMatrix()[i][j]);
                btn[i][j].setIcon(icon);
                add(btn[i][j]);
            }
        }
    }
    
    public void continueArrayButton(int[][] matrix) {
        btn = new JButton[row][col];
        for (int i = 1; i < row - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                btn[i][j] = createButton(i + "," + j);
                System.out.println("matrix: " + matrix[i][j]);
                Icon icon = getIcon(matrix[i][j]);
                btn[i][j].setIcon(icon);
                if (matrix[i][j] == 0) {
                    setDisable(btn[i][j]);
                }
                add(btn[i][j]);
            }
        }
    }

    private Icon getIcon(int index) {
        int width = 48, height = 48;
        Image image = new ImageIcon(getClass().getResource(
                "/icon/" + index + ".png")).getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(width, height,
                image.SCALE_SMOOTH));
        return icon;
    }

    private JButton createButton(String action) {
        JButton btn = new JButton();
        btn.setActionCommand(action);
        btn.setBorder(null);
        btn.addActionListener(this);
        return btn;
    }

    public void execute(Point p1, Point p2) {
        System.out.println("delete");
        setDisable(btn[p1.x][p1.y]);
        setDisable(btn[p2.x][p2.y]);
    }

    private void setDisable(JButton btn) {
        btn.setIcon(null);
        btn.setBackground(backGroundColor);
        btn.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String btnIndex = e.getActionCommand();
        int indexDot = btnIndex.lastIndexOf(",");
        int x = Integer.parseInt(btnIndex.substring(0, indexDot));
        int y = Integer.parseInt(btnIndex.substring(indexDot + 1,
                btnIndex.length()));
        if (p1 == null) {
            p1 = new Point(x, y);
            btn[p1.x][p1.y].setBorder(new LineBorder(Color.red));
        } else {
            p2 = new Point(x, y);
            System.out.println("(" + p1.x + "," + p1.y + ")" + " --> " + "("
                    + p2.x + "," + p2.y + ")");
            line = algorithm.checkTwoPoint(p1, p2);
            if (line != null) {
                System.out.println("line != null");
                algorithm.getMatrix()[p1.x][p1.y] = 0;
                algorithm.getMatrix()[p2.x][p2.y] = 0;
                algorithm.showMatrix();
                execute(p1, p2);
                line = null;
                score += 10;
                item--;
                frame.time++;
                frame.lbScore.setText(score + "");
            }
            btn[p1.x][p1.y].setBorder(null);
            p1 = null;
            p2 = null;
            System.out.println("done");
            if (item == 0) {
                File file = new File("ID_User.txt");
                Scanner reader;
                try {
                    reader = new Scanner(file);
                    int data = Integer.parseInt(reader.nextLine());
                    System.out.println("data: " + data);
                    Database db = new Database();
                    db.CheckConn(db_user, db_pass);
                    db.setHightResult(data, frame.time, score);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ButtonEvent.class.getName()).log(Level.SEVERE, null, ex);
                }                
                ap.stop();
                as = m.winningMusic();
                ap.start(as);
                if (frame.showDialogNewGame(
                        "You are winer!\nDo you want play again?", "Win", 1) == true) {
                    ap.stop(as);
                };
            }
        }
    }
}