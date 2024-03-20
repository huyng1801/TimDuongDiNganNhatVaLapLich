package Graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
//Class để vẽ và lưu đồ thị
public class GraphDrawer {

    private Set<Vertex> vertices;
    private List<Edge> edges;
    private List<Edge> shortestPath;
    private Color shortestPathColor = Color.RED;
    private int shortestPathCost;
    private JFrame frame;
    private CustomPanel customPanel;

    public GraphDrawer(Set<Vertex> vertices, List<Edge> edges, List<Edge> shortestPath, int shortestPathCost) {
        this.vertices = vertices;
        this.edges = edges;
        this.shortestPath = shortestPath;
        this.shortestPathCost = shortestPathCost;
    }

    public void showGraph() {
        frame = new JFrame("Graph Viewer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        customPanel = new CustomPanel();
        customPanel.addMouseListener(customPanel);
        customPanel.addMouseMotionListener(customPanel);
        frame.add(customPanel);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if ((KeyEvent.CTRL_DOWN_MASK) != 0 && e.getKeyCode() == KeyEvent.VK_P) {

                    exportToJPG();
                }
            }
        });
        frame.setVisible(true);
    }

    private void exportToJPG() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Graph Image");
        int userSelection = fileChooser.showSaveDialog(frame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            BufferedImage image = new BufferedImage(customPanel.getWidth(), customPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            customPanel.paintComponent(g2d);
            g2d.dispose();

            try {
                File outputfile = new File(fileToSave.getAbsolutePath() + ".jpg");
                ImageIO.write(image, "jpg", outputfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class CustomPanel extends JPanel implements MouseListener, MouseMotionListener {

        private Vertex selectedVertex;
        private int initialMouseX;
        private int initialMouseY;
        private int initialVertexX;
        private int initialVertexY;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (Edge edge : edges) {
                Vertex vertex1 = edge.getVertex1();
                Vertex vertex2 = edge.getVertex2();
                int x1 = vertex1.getX();
                int y1 = vertex1.getY();
                int x2 = vertex2.getX();
                int y2 = vertex2.getY();

                g.setColor(Color.blue);

                for (Edge shortestPathEdge : shortestPath) {
                    if (shortestPathEdge.getVertex1().getVertexName().equals(edge.getVertex1().getVertexName())
                            && shortestPathEdge.getVertex2().getVertexName().equals(edge.getVertex2().getVertexName())) {
                        g.setColor(shortestPathColor);
               
                        break;  
                    }
                }

                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawLine(x1, y1, x2, y2);
                drawArrow(g, x1, y1, x2, y2);

                g.setColor(Color.black);
                g.fillOval(x1 - 5, y1 - 5, 10, 10);
                g.fillOval(x2 - 5, y2 - 5, 10, 10);

                g.setColor(Color.black);
                g.drawString(vertex1.getVertexName(), x1 - 10, y1 - 20);
                g.drawString(vertex2.getVertexName(), x2 - 10, y2 - 20);
                g.drawString(Integer.toString(edge.getWeight()), (x1 + x2) / 2, (y1 + y2) / 2);
            }

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Chi phí đường đi ngắn nhất: " + shortestPathCost, 10, 30);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            for (Vertex vertex : vertices) {
                int x = vertex.getX();
                int y = vertex.getY();
                if (Math.abs(mouseX - x) <= 10 && Math.abs(mouseY - y) <= 10) {
                    selectedVertex = vertex;
                    initialMouseX = mouseX;
                    initialMouseY = mouseY;
                    initialVertexX = x;
                    initialVertexY = y;
                    break;
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (selectedVertex != null) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int deltaX = mouseX - initialMouseX;
                int deltaY = mouseY - initialMouseY;
                selectedVertex.setX(initialVertexX + deltaX);
                selectedVertex.setY(initialVertexY + deltaY);
                repaint();
                for (Edge edge : edges) {
                    if (edge.getVertex1() == selectedVertex) {
                        edge.updateStartPoint(selectedVertex);
                    } else if (edge.getVertex2() == selectedVertex) {
                        edge.updateEndPoint(selectedVertex);
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            selectedVertex = null;
        }

        private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
            double angle = Math.atan2(y2 - y1, x2 - x1);
            int arrowSize = 10;

            Polygon arrowHead = new Polygon();
            arrowHead.addPoint(x2, y2);
            arrowHead.addPoint((int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6)),
                    (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6)));
            arrowHead.addPoint((int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6)),
                    (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6)));

            g.setColor(Color.blue);
            g.fillPolygon(arrowHead);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

}
