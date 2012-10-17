package visual.gui.graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.data.general.DefaultPieDataset;

import visual.data.DirectoryTree;
import visual.data.Node;
import visual.gui.PieChart;
import visual.main.VisualHDD;

/**
 * This class handles the creation and handling of the charts representing the file scans
 * @author Calvin Nefdt - 207524322
 *
 */
public class DefaultGraphPanel extends JPanel implements ChartMouseListener {
	private static final long serialVersionUID = 1L;
	
	DefaultPieDataset data;
	Node parentNode;	
	File[] partitionInfo;
	ChartPanel chart;
	ArrayList<Node> trace = new ArrayList<Node>(0);
	
	/**
	 * Keeps track of which slice the mouse is currently placed on
	 */
	private Object hoverSlice;
	
	private JPopupMenu popup;
	
	/**
	 * Buttons for the context menu
	 */
	private JMenuItem backMenuItem, deleteMenuItem;
	
	/**
	 * Keeps track of the current browsed-to path
	 */
	private String path;
		
	/**
	 * Sets up the data for needed to generate a Piechart 
	 * @param fileInfo The data representing the file system partitions
	 * @param node The parent node that the current view is looking at
	 */
	public DefaultGraphPanel(File[] fileInfo, Node node){		
		parentNode = node;
		partitionInfo = fileInfo;
		drawGraph();
	}
	
	/**
	 * Draw's the Pie chart
	 */
	public void drawGraph(){
		if(parentNode == null){
			data = new DefaultPieDataset();
			
			for(int i=0; i<partitionInfo.length; i++){
				data.setValue(partitionInfo[i].getAbsolutePath(),partitionInfo[i].getTotalSpace());
			}
			
			PieChart pi = new PieChart("Overview", data);
			chart = pi.createChart();
		}
		
		else try{
			data = new DefaultPieDataset();
			Node[] child = parentNode.getChildren();
			
			for(int i=0; i<child.length; i++){
				data.setValue(child[i].getName(), child[i].getSize());
			}
			
			PieChart pi = new PieChart(parentNode.getPath(), data);
			chart = pi.createChart();
		}
		catch(NullPointerException e){
			JOptionPane.showMessageDialog(new JFrame(), "The element you have clicked on is not a valid area to scan further." +
					" Some Possibilities as to why : \n" +
					" - The location is a file and not a directory.\n" +
					" - The area you have clicked on is not a valid scanning area.", "Bottom level", JOptionPane.ERROR_MESSAGE);
		}
		removeAll();
		
		popup = chart.getPopupMenu();
		popup.removeAll();
		backMenuItem = new JMenuItem("Back");
		backMenuItem.addActionListener(new ContextMenuListener());
		popup.add(backMenuItem);
		deleteMenuItem = new JMenuItem("Delete");
		deleteMenuItem.addActionListener(new ContextMenuListener());
		popup.add(deleteMenuItem);
		chart.setPopupMenu(popup);
		
//		menu.addActionListener(new ActionListener(){
//			  public void actionPerformed(ActionEvent e){}
//			  });
		
		chart.addChartMouseListener(this);
		add(chart);
	}
	
	 /**
     * Update the pie chart as necessary for redrawing
     * @param node The parent Node that is being viewed;
     */
	  public void update(Node node){         
          if(trace.size() >0 && parentNode != trace.get(trace.size()-1)){
                  if(node != trace.get(trace.size()-1)){
                          trace.add(parentNode);
                  }
          }
          else if(trace.size()==0)                       
                  trace.add(parentNode);
         
                 
          parentNode = node;
          if (node != null)
        	  path = node.getPath();
          drawGraph();
          revalidate();
  }
	
	
	public Node getParentNode(){
		return trace.get(trace.size()-1);
	}
	
	public void removeParentNode(){
		
		trace.remove(trace.size()-1);
		
	}
	
	public int getTraceSize(){
		return trace.size();
	}
	
	public void back()
	{
		if(getTraceSize()!=0){					
			update(getParentNode());					
			removeParentNode();
		}
	}
	
	@Override
	public void chartMouseClicked(ChartMouseEvent event) {
		if(parentNode!=null){
			try{
				boolean updated = false;
				ChartEntity chartentity = event.getEntity();
				Node[] tempNode = parentNode.getChildren();
		
				String result = chartentity.getToolTipText();
				int cutOff = result.lastIndexOf(58);
				result = result.substring(0, cutOff);
				
				for(int i=0;i<tempNode.length; i++)
					if(result.equals(tempNode[i].getName()) && !tempNode[i].isChildNode()){
						update(tempNode[i]);
						updated = true;
						break;
					}
				if(!updated)
					throw new NullPointerException();
			}
			catch(NullPointerException e){
				JOptionPane.showMessageDialog(new JFrame(), "The element you have clicked on is not a valid area to scan further." +
															" Some Possibilities as to why : \n" +
															" - The location is a file and not a directory.\n" +
															" - The area you have clicked on is not a valid scanning area.", "Bottom level", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			
		}
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent arg0){
		hoverSlice = arg0.getEntity();
	}
	
	private class ContextMenuListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == backMenuItem)
			{
				back();
			}
			
			else if (arg0.getSource() == deleteMenuItem)
			{
				String s = hoverSlice.toString();
				String nodePath = path + "\\" + s.substring(s.indexOf("(")+1, s.lastIndexOf(")"));
				int index = VisualHDD.getTreeIndex(nodePath.substring(0, 2));
				DirectoryTree tree = VisualHDD.getTree(index);
				if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + nodePath + "?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
				{
					tree.delete(nodePath);
					update(parentNode);
				}
			}
		}
	}
}