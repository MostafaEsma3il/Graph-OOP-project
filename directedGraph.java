package sample;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.Group;

import javafx.util.Pair;

class DirectedGraph extends Graph{
    public String[] addEdges(String from[], String to[]){
        String ret[] = new String[from.length];
        for (int i = 0; i < from.length; ++i){
            if (index.get(from[i]) == null|| index.get(to[i]) == null){
                ret[i] = "One of the nodes are not found";
                Main.errorLabel.setText(ret[i]);
            }
            else if (values.get(index.get(from[i])).equals("-1") || values.get(index.get(to[i])).equals("-1")){
                ret[i] = "One of the nodes are not found";
                Main.errorLabel.setText(ret[i]);
            }
            else if (graphRep[index.get(from[i])][index.get(to[i])] != 0){
                ret[i] = "Edge Already Found";
                Main.errorLabel.setText(ret[i]);
            }
            else if (from[i].equals(to[i])){
                ret[i] = "Cannot Add Edge From A Node To Itself";
                Main.errorLabel.setText(ret[i]);
            }
            else{
                graphRep[index.get(from[i])][index.get(to[i])] = 1;
                ret[i] = "Edge Added Successfully";
                Main.errorLabel.setText("");
            }
        }
        draw();
        return ret;
    }
    public String[] removeEdges(String from[], String to[]){
        String ret[] = new String[from.length];
        for (int i = 0; i < from.length; ++i) {
            if (!values.containsValue(from[i]) || !values.containsValue(to[i])){
                ret[i] = "One of the nodes are not found";
                Main.errorLabel.setText(ret[i]);
            }
            else if (graphRep[index.get(from[i])][index.get(to[i])] == 0){
                ret[i] = "Edge is not Found";
                Main.errorLabel.setText(ret[i]);
            }
            else{
                graphRep[index.get(from[i])][index.get(to[i])] = 0;
                ret[i] = "Edge Removed Successfully";
                Main.errorLabel.setText("");
            }
        }
        draw();
        return ret;
    }
    public String[] updateWeights(String from[], String to[], String weight[]){
        String ret[] = new String[from.length];
        try{
            for (int i = 0; i < from.length; ++i) {
                if (!values.containsValue(from[i]) || !values.containsValue(to[i])){
                    ret[i] = "One of the nodes are not found";
                    Main.errorLabel.setText(ret[i]);
                }
                else if (from[i].equals(to[i])){
                    ret[i] = "Cannot Add Edge From A Node To Itself";
                    Main.errorLabel.setText(ret[i]);
                }
                else{
                    if (graphRep[index.get(from[i])][index.get(to[i])] == 0)
                        addEdges(from, to);
                    int WeightInteger = Integer.parseInt(weight[i]);
                    graphRep[index.get(from[i])][index.get(to[i])] = WeightInteger;
                    ret[i] = "Edge Updated Successfully";
                    if(from[i].equals(to[i])) Main.errorLabel.setText("Cannot Add Edge From A Node To Itself");
                    else Main.errorLabel.setText("");
                }
            }
        }
        catch (Exception e){
            ret[0] = "Weight Must Be An Integer";
            Main.errorLabel.setText(ret[0]);
            System.out.println(e);
        }
        draw();
        return ret;
    }
    public int sumWeights(String from[], String to[]){
        int ret = 0;
        for (int i = 0; i < from.length; ++i){
            if (!values.containsValue(from[i])|| !values.containsValue(to[i])){
                Main.errorLabel.setText("One Of The Nodes Are Not Found");
                break;
            }
            else if(edgeInSumPath.containsKey(new Pair(index.get(from[i]), index.get(to[i])))){
                Main.errorLabel.setText("Edge is Already Added To The Sum");
                break;
            }
            else if(graphRep[index.get(from[i])][index.get(to[i])] == 0){
                Main.errorLabel.setText("Edge is Not Found");
                break;
            }
            ret += graphRep[index.get(from[i])][index.get(to[i])];
            nodeInSumPath.put(index.get(from[i]), true);
            nodeInSumPath.put(index.get(to[i]), true);
            edgeInSumPath.put(new Pair(index.get(from[i]), index.get(to[i])), true);
            Main.errorLabel.setText("");
        }
        draw();
        return ret;
    }
    public void draw(){
        Main.nodes.clear();
        Main.edges.clear();
        Main.weights.clear();
        Main.rays.clear();
        drawNodes(1400/2, 950/2);
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j) {
                if (graphRep[i][j] >= 1) {
                    Color color = Color.BLACK;
                    Pair f = GUIIndex.get(i);
                    Pair t = GUIIndex.get(j);
                    if (edgeInSumPath.containsKey(new Pair(i, j))) color = Color.RED;
                    Main.addGUIEdge(values.get(i), values.get(j),graphRep[i][j], color);
                }
            }
        renderLayout();
    }
    public void renderLayout(){
        Group layout = new Group();
        for (int i = 0; i < Main.nodes.size(); ++i){
            layout.getChildren().add(Main.nodes.get(i));
            System.out.println(Main.nodes.get(i).getLayoutBounds().getWidth());
        }

        for (int i = 0; i < Main.edges.size(); ++i)
            layout.getChildren().add(Main.edges.get(i));
        for (int i = 0; i < Main.weights.size(); ++i)
            layout.getChildren().add(Main.weights.get(i));
        for (int i = 0; i < Main.rays.size(); ++i)
            layout.getChildren().add(Main.rays.get(i));
        layout.getChildren().addAll(Main.menuLayout);
        Line vLine = new Line(1400, 0, 1400, 950);
        layout.getChildren().addAll(vLine);
        Scene scene = new Scene(layout, 1900, 950);

        

	Main.window.setScene(scene);
    }
}