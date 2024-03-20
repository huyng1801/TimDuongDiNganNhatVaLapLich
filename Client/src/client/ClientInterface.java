package client;

import Graph.Edge;
import Graph.GraphData;
import Graph.GraphDataProcessor;
import Graph.GraphDrawer;
import Scheduler.GanttChartItem;
import Scheduler.ProcessDataReader;
import Scheduler.Process;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.spec.KeySpec;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ClientInterface extends javax.swing.JFrame {

    Socket serverConnection;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private GraphData graphData;
    private List<Process> processes;

    public ClientInterface() {
        initComponents();
        groupRadio();
        connectToServerAsync();
    }

    public static SecretKey generateKeyFromPassword(String password, byte[] salt) throws Exception {
        int iterations = 10000; // số lần lặp
        int keyLength = 128; // độ dài khóa (128, 192, hoặc 256 bits)

        // Tạo một đối tượng KeySpec từ mật khẩu và muối
        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);

        // Sử dụng SecretKeyFactory để tạo khóa từ KeySpec
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] keyBytes = keyFactory.generateSecret(keySpec).getEncoded();

        // Sử dụng keyBytes để tạo SecretKey
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        return secretKey;
    }

    private byte[] encryptObject(Object obj) throws Exception {
        String password = "YourSecretPassword"; // Chuỗi mật khẩu
        byte[] salt = new byte[16]; // Một giá trị salt ngẫu nhiên (bạn nên sử dụng giá trị salt thực tế)

        SecretKey generatedKey = generateKeyFromPassword(password, salt);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, generatedKey);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();

        byte[] objectBytes = byteArrayOutputStream.toByteArray();

        return cipher.doFinal(objectBytes);
    }

    private Object decryptObject(byte[] encryptedBytes) throws Exception {
        String password = "YourSecretPassword"; // Chuỗi mật khẩu
        byte[] salt = new byte[16]; // Một giá trị salt ngẫu nhiên (bạn nên sử dụng giá trị salt thực tế)

        SecretKey generatedKey = generateKeyFromPassword(password, salt);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, generatedKey);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decryptedBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        Object obj = objectInputStream.readObject();
        objectInputStream.close();

        return obj;
    }

    private void groupRadio() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbFCFS);
        bg.add(rbSJF);
        bg.add(rbPriority);
        bg.add(rbRoundRobin);
        rbFCFS.setSelected(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        btnSelectFileGraph = new javax.swing.JButton();
        txtPathFileGraph = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGraph = new javax.swing.JTable();
        labelSource = new javax.swing.JLabel();
        labelDestination = new javax.swing.JLabel();
        btnSendGraph = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        rbFCFS = new javax.swing.JRadioButton();
        rbPriority = new javax.swing.JRadioButton();
        rbRoundRobin = new javax.swing.JRadioButton();
        rbSJF = new javax.swing.JRadioButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableScheduler = new javax.swing.JTable();
        txtPathFileScheduler = new javax.swing.JTextField();
        btnSelectFileScheduler = new javax.swing.JButton();
        btnSendScheduler = new javax.swing.JButton();
        labelQuantum = new javax.swing.JLabel();
        txtQuantum = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        btnSelectFileGraph.setText("Chọn file");
        btnSelectFileGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectFileGraphActionPerformed(evt);
            }
        });

        txtPathFileGraph.setEditable(false);

        tableGraph.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Source", "Destination", "Trọng số"
            }
        ));
        jScrollPane1.setViewportView(tableGraph);
        if (tableGraph.getColumnModel().getColumnCount() > 0) {
            tableGraph.getColumnModel().getColumn(0).setResizable(false);
            tableGraph.getColumnModel().getColumn(0).setHeaderValue("Source");
            tableGraph.getColumnModel().getColumn(1).setHeaderValue("Destination");
            tableGraph.getColumnModel().getColumn(2).setHeaderValue("Trọng số");
        }

        labelSource.setText("Source:");

        labelDestination.setText("Desctination:");

        btnSendGraph.setText("Gửi đồ thị");
        btnSendGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendGraphActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtPathFileGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSelectFileGraph))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(labelSource, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(labelDestination, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSendGraph)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelectFileGraph)
                    .addComponent(txtPathFileGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelSource)
                    .addComponent(labelDestination)
                    .addComponent(btnSendGraph))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Tìm đường đi", jPanel3);

        rbFCFS.setText("FCFS");
        rbFCFS.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbFCFSItemStateChanged(evt);
            }
        });

        rbPriority.setText("Priority");
        rbPriority.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbPriorityItemStateChanged(evt);
            }
        });

        rbRoundRobin.setText("Round Robin");
        rbRoundRobin.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbRoundRobinItemStateChanged(evt);
            }
        });

        rbSJF.setText("SJF");
        rbSJF.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rbSJFItemStateChanged(evt);
            }
        });

        tableScheduler.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tableScheduler);

        txtPathFileScheduler.setEditable(false);

        btnSelectFileScheduler.setText("Chọn file");
        btnSelectFileScheduler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectFileSchedulerActionPerformed(evt);
            }
        });

        btnSendScheduler.setText("Gửi tiến trình");
        btnSendScheduler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendSchedulerActionPerformed(evt);
            }
        });

        labelQuantum.setText("Quantum:");

        txtQuantum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQuantumKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(rbFCFS, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(rbSJF, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(rbPriority, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(rbRoundRobin, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                            .addComponent(txtPathFileScheduler, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSelectFileScheduler))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(labelQuantum, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtQuantum, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSendScheduler))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbFCFS)
                    .addComponent(rbRoundRobin)
                    .addComponent(rbPriority)
                    .addComponent(rbSJF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSelectFileScheduler)
                    .addComponent(txtPathFileScheduler, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSendScheduler)
                    .addComponent(labelQuantum)
                    .addComponent(txtQuantum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        jTabbedPane2.addTab("Lập lịch CPU", jPanel4);

        getContentPane().add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectFileGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectFileGraphActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                txtPathFileGraph.setText(filePath);
                graphData = GraphDataProcessor.readGraphDataFromFile(filePath);
                DefaultTableModel model = (DefaultTableModel) tableGraph.getModel();
                model.setRowCount(0);
                labelSource.setText("Source: " + graphData.getSource());
                labelDestination.setText("Destination: " + graphData.getDestination());

                for (Edge edge : graphData.getEdges()) {
                    Object[] row = {edge.getVertex1().getVertexName(), edge.getVertex2().getVertexName(), edge.getWeight()};
                    model.addRow(row);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }//GEN-LAST:event_btnSelectFileGraphActionPerformed

    private void btnSelectFileSchedulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectFileSchedulerActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                txtPathFileScheduler.setText(filePath);
                if (rbFCFS.isSelected() || rbSJF.isSelected() || rbRoundRobin.isSelected()) {
                    loadFCFSData(filePath);
                } else {
                    loadPriorityData(filePath);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnSelectFileSchedulerActionPerformed

    private void rbFCFSItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbFCFSItemStateChanged

        ressetTableScheduler();
    }//GEN-LAST:event_rbFCFSItemStateChanged

    private void rbSJFItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbSJFItemStateChanged

        ressetTableScheduler();
    }//GEN-LAST:event_rbSJFItemStateChanged
    private void ressetTableScheduler() {
        DefaultTableModel model = (DefaultTableModel) tableScheduler.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        txtPathFileScheduler.setText("");
        txtQuantum.setText("");
        txtQuantum.setVisible(false);
        labelQuantum.setVisible(false);
    }

    public void sendExitSignalToServer() {
        try {
//            out.writeObject("exit");
//            out.flush();
            out.writeObject(encryptObject("exit"));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void rbPriorityItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbPriorityItemStateChanged

        ressetTableScheduler();
    }//GEN-LAST:event_rbPriorityItemStateChanged

    private void rbRoundRobinItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rbRoundRobinItemStateChanged

        ressetTableScheduler();
        txtQuantum.setVisible(true);
        labelQuantum.setVisible(true);
    }//GEN-LAST:event_rbRoundRobinItemStateChanged

private void connectToServerAsync() {
    SwingUtilities.invokeLater(() -> {
        try {
            String inputIP = JOptionPane.showInputDialog(rootPane, "Nhập địa chỉ IP Server:");
            
            if (inputIP != null) {
                if (!inputIP.isEmpty()) {
                    serverConnection = new Socket(inputIP, 12345);
                    out = new ObjectOutputStream(serverConnection.getOutputStream());
                    in = new ObjectInputStream(serverConnection.getInputStream());
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Địa chỉ IP Server không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.exit(0); 
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, "Địa chỉ IP Server không chính xác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            //ex.printStackTrace();
            connectToServerAsync();
        }
    });
}


    private void btnSendGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendGraphActionPerformed
        if (txtPathFileGraph.getText().equals("") || tableGraph.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn file dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (serverConnection != null) {
            try {
                out.writeObject(encryptObject("graph"));
                out.flush();

//                out.writeObject(graphData);
//                out.flush();
                byte[] encryptedGraphData = encryptObject(graphData);
                out.writeObject(encryptedGraphData);
                out.flush();
                byte[] encryptedResponse = (byte[]) in.readObject();
                String response = (String) decryptObject(encryptedResponse);
//                String response = (String) in.readObject();

                if ("graphValid".equals(response)) {
                    byte[] encryptedReceivedGraph = (byte[]) in.readObject();
                    GraphData receivedGraph = (GraphData) decryptObject(encryptedReceivedGraph);
                    byte[] encryptedReceivedShortedPath = (byte[]) in.readObject();
                    List<Edge> receivedShortedPath = (List<Edge>) decryptObject(encryptedReceivedShortedPath);
                   
                    byte[] encryptedReceiverCost = (byte[]) in.readObject();
                    int receiverCost = (Integer) decryptObject(encryptedReceiverCost);
//                    GraphData receivedGraph = (GraphData) in.readObject();
//                    List<Edge> receivedShortedPath = (List<Edge>) in.readObject();
//                    int receiverCost = (Integer) in.readObject();
                    GraphDrawer gd = new GraphDrawer(receivedGraph.getVertices(), receivedGraph.getEdges(), receivedShortedPath, receiverCost);
                    gd.showGraph();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnSendGraphActionPerformed

    private void btnSendSchedulerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendSchedulerActionPerformed
        if (txtPathFileScheduler.getText().equals("") || tableScheduler.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng chọn file dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ("".equals(txtQuantum.getText()) && rbRoundRobin.isSelected()) {
            JOptionPane.showMessageDialog(rootPane, "Vui lòng nhập quantum!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (serverConnection != null) {
            try {
                String schedulerAlgorithms;
                int quantum = 0;
                if (rbFCFS.isSelected()) {
                    schedulerAlgorithms = "FCFS";
                } else if (rbSJF.isSelected()) {
                    schedulerAlgorithms = "SJF";
                } else if (rbPriority.isSelected()) {
                    schedulerAlgorithms = "Priority";
                } else {
                    schedulerAlgorithms = "RR";
                    quantum = Integer.parseInt(txtQuantum.getText());
                };
                byte[] encryptedSchedulerAlgorithms = encryptObject(schedulerAlgorithms);
                out.writeObject(encryptedSchedulerAlgorithms);
                out.flush();
//                out.writeObject(schedulerAlgorithms);
//                out.flush();
//                out.writeObject(processes);
//                out.flush();
                byte[] encryptedProcesses = encryptObject(processes);
                out.writeObject(encryptedProcesses);
                out.flush();
                if (quantum != 0) {
//                    out.writeObject(quantum);
//                    out.flush();
                    byte[] encryptedQuantum = encryptObject(quantum);
                    out.writeObject(encryptedQuantum);
                    out.flush();
                }
//                String response = (String) in.readObject();
                byte[] encryptedResponse = (byte[]) in.readObject();
                String response = (String) decryptObject(encryptedResponse);
                if ("schedulerValid".equals(response)) {
//                    List<GanttChartItem> receivedGannt = (List<GanttChartItem>) in.readObject();
//                    List<Process> receivedProcesses = (List<Process>) in.readObject();
//                    double averageWaitingTime = (Double) in.readObject();
//                    double averageTurnaroundTime = (Double) in.readObject();
                    byte[] encryptedReceivedGannt = (byte[]) in.readObject();
                    List<GanttChartItem> receivedGannt = (List<GanttChartItem>) decryptObject(encryptedReceivedGannt);
                    byte[] encryptedReceivedProcesses = (byte[]) in.readObject();
                    List<Process> receivedProcesses = (List<Process>) decryptObject(encryptedReceivedProcesses);

                    byte[] encryptedAverageWaitingTime = (byte[]) in.readObject();
                    double averageWaitingTime = (Double) decryptObject(encryptedAverageWaitingTime);
                    byte[] encryptedAverageTurnaroundTime = (byte[]) in.readObject();
                    double averageTurnaroundTime = (Double) decryptObject(encryptedAverageTurnaroundTime);

                    Gantt fr = new Gantt();
                    fr.drawChart(receivedGannt, receivedProcesses, averageWaitingTime, averageTurnaroundTime);
                    fr.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Tiến trình khong hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ClientInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnSendSchedulerActionPerformed

    private void txtQuantumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantumKeyTyped
        if (!Character.isDigit(evt.getKeyChar()))
            evt.consume();
    }//GEN-LAST:event_txtQuantumKeyTyped

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        sendExitSignalToServer();
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing
    private void loadFCFSData(String filePath) {
        processes = ProcessDataReader.readFCFSDataFromFile(filePath);
        DefaultTableModel model = (DefaultTableModel) tableScheduler.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        model.addColumn("Process");
        model.addColumn("Arrival time");
        model.addColumn("Burst time");
        for (Process process : processes) {
            Object[] row = {
                process.getName(),
                process.getArrivalTime(),
                process.getBurstTime()
            };
            model.addRow(row);
        }
    }

    private void loadPriorityData(String filePath) {
        processes = ProcessDataReader.readPriorityDataFromFile(filePath);
        DefaultTableModel model = (DefaultTableModel) tableScheduler.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
        model.addColumn("Process");
        model.addColumn("Arrival time");
        model.addColumn("Burst time");
        model.addColumn("Priority");
        for (Process process : processes) {
            Object[] row = {
                process.getName(),
                process.getArrivalTime(),
                process.getBurstTime(),
                process.getPriority()
            };
            model.addRow(row);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelectFileGraph;
    private javax.swing.JButton btnSelectFileScheduler;
    private javax.swing.JButton btnSendGraph;
    private javax.swing.JButton btnSendScheduler;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel labelDestination;
    private javax.swing.JLabel labelQuantum;
    private javax.swing.JLabel labelSource;
    private javax.swing.JRadioButton rbFCFS;
    private javax.swing.JRadioButton rbPriority;
    private javax.swing.JRadioButton rbRoundRobin;
    private javax.swing.JRadioButton rbSJF;
    private javax.swing.JTable tableGraph;
    private javax.swing.JTable tableScheduler;
    private javax.swing.JTextField txtPathFileGraph;
    private javax.swing.JTextField txtPathFileScheduler;
    private javax.swing.JTextField txtQuantum;
    // End of variables declaration//GEN-END:variables
}
