package Controller;

import Model.DataByYear_Model;
import Model.Data_Model;
import Model.Location_Model;
import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertNotEquals;

public class Controller{

    private Data_Model data;

    private HashMap<Location_Model, MeshView> laMap;
    private HashMap<Location_Model, Cylinder> histoMap;

    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;

    @FXML
    private AnchorPane myAnchorPane;

    @FXML
    private Pane myPane;

    @FXML
    private Label myYear;

    @FXML
    private Slider mySlider;

    @FXML
    private RadioButton radioHistogram;

    @FXML
    private RadioButton radioQuadrilater;

    @FXML
    private Button ButtonStop;

    @FXML
    private Button ButtonPause;

    @FXML
    private Button ButtonPlay;

    @FXML
    private TextField speedLecture;

    @FXML
    private Label LatitudeLabel;

    @FXML
    private Label LongitudeLabel;

    @FXML
    private CheckBox myGraph;

    @FXML
    private LineChart graphic;

    @FXML
    public void initialize(){
        data = new Data_Model("resources/tempanomaly_4x4grid.csv");


        mySlider.setMin(1880);
        mySlider.setMax(2020);
        mySlider.setValue(1880);
        myYear.setText("YEAR : 1880");

        myGraph.setSelected(true);

        speedLecture.setText("1");

        laMap = new HashMap<>();
        histoMap = new HashMap<>();

        //Create a Pane et graph scene root for the 3D content
        Group root3D = new Group();

        // Load geometry
        ObjModelImporter objImporter = new ObjModelImporter();
        try{
            URL modelUrl = this.getClass().getResource("Earth/earth.obj");
            objImporter.read(modelUrl);
        }catch (ImportException e){
            System.out.println(e.getMessage());
        }

        MeshView[] meshViews = objImporter.getImport();
        Group earth = new Group(meshViews);

        //Add earth to this node
        root3D.getChildren().addAll(earth);

        // Add a camera group
        PerspectiveCamera camera = new PerspectiveCamera(true);

        //Build camera manager
        CameraManager cam = new CameraManager(camera,myPane,root3D);

        // Add point light
        PointLight light = new PointLight(javafx.scene.paint.Color.WHITE);
        light.setTranslateX(-180);
        light.setTranslateY(-90);
        light.setTranslateZ(-120);
        light.getScope().addAll(root3D);
        root3D.getChildren().add(light);

        // Add ambient light
        AmbientLight ambientLight = new AmbientLight(javafx.scene.paint.Color.WHITE);
        ambientLight.getScope().addAll(root3D);
        root3D.getChildren().add(ambientLight);

        // Create scene
        SubScene scene = new SubScene(root3D, 501, 349, true, SceneAntialiasing.BALANCED );
        scene.setCamera(camera);
        scene.setFill(Color.gray(0.12));
        myPane.getChildren().addAll(scene);

        // Pour la légende
        Color color1 = new Color(1,0,0, 0.4);
        final PhongMaterial Color1 = new PhongMaterial();
        Color1.setDiffuseColor(color1);
        Color1.setSpecularColor(color1);
        Rectangle rect1 = new Rectangle(15, 15, 15, 15);
        rect1.setFill(color1);
        rect1.setTranslateY(10);
        Rectangle rectRed = new Rectangle(15,15,15, 70);
        rectRed.setFill(color1);

        Color color2 = new Color(1,0.3,0, 0.4);
        final PhongMaterial Color2 = new PhongMaterial();
        Color2.setDiffuseColor(color2);
        Color2.setSpecularColor(color2);
        Rectangle rect2 = new Rectangle(15, 15, 15, 15);
        rect2.setFill(color2);
        rect2.setTranslateY(25);

        Color color3 = new Color(1,0.6,0, 0.4);
        final PhongMaterial Color3 = new PhongMaterial();
        Color3.setDiffuseColor(color3);
        Color3.setSpecularColor(color3);
        Rectangle rect3 = new Rectangle(15, 15, 15, 15);
        rect3.setFill(color3);
        rect3.setTranslateY(40);

        Color color4 = new Color(1,0.8,0, 0.4);
        final PhongMaterial Color4 = new PhongMaterial();
        Color4.setDiffuseColor(color4);
        Color4.setSpecularColor(color4);
        Rectangle rect4 = new Rectangle(15, 15, 15, 15);
        rect4.setFill(color4);
        rect4.setTranslateY(55);

        Color color5 = new Color(0.3,0.9,1, 0.4);
        final PhongMaterial Color5 = new PhongMaterial();
        Color5.setDiffuseColor(color5);
        Color5.setSpecularColor(color5);
        Rectangle rect5 = new Rectangle(15, 15, 15, 15);
        rect5.setFill(color5);
        rect5.setTranslateY(70);

        Color color6 = new Color(0,0.6,1, 0.4);
        final PhongMaterial Color6 = new PhongMaterial();
        Color6.setDiffuseColor(color6);
        Color6.setSpecularColor(color6);
        Rectangle rect6 = new Rectangle(15, 15, 15, 15);
        rect6.setFill(color6);
        rect6.setTranslateY(85);

        Color color7 = new Color(0,0.3,1, 0.4);
        final PhongMaterial Color7 = new PhongMaterial();
        Color7.setDiffuseColor(color7);
        Color7.setSpecularColor(color7);
        Rectangle rect7 = new Rectangle(15, 15, 15, 15);
        rect7.setFill(color7);
        rect7.setTranslateY(100);

        Color color8 = new Color(0,0,1, 0.4);
        final PhongMaterial Color8 = new PhongMaterial();
        Color8.setDiffuseColor(color8);
        Color8.setSpecularColor(color8);
        Rectangle rect8 = new Rectangle(15, 15, 15, 15);
        rect8.setFill(color8);
        rect8.setTranslateY(115);

        Color color9 = new Color(0.1,0.1,0.1, 0.7);
        final PhongMaterial Color9 = new PhongMaterial();
        Color9.setDiffuseColor(color9);
        Color9.setSpecularColor(color9);

        PhongMaterial transparent = new PhongMaterial();
        transparent.setSpecularColor(Color.TRANSPARENT);
        transparent.setDiffuseColor(Color.TRANSPARENT);

        Rectangle rectBlue = new Rectangle(15, 15, 15, 70);
        rectBlue.setFill(color8);
        rectBlue.setTranslateY(70);

        Label min = new Label();
        min.setText(Float.toString(data.getMin()));
        min.setTextFill(Color.WHITE);
        min.setTranslateY(150);

        Label max = new Label();
        max.setText(Float.toString(data.getMax()));
        max.setTextFill(Color.WHITE);

        initQuadri(earth, transparent);
        initHisto(earth, transparent);

        Group root = new Group();

        SubScene sub = new SubScene(root, 40, 200, true, SceneAntialiasing.BALANCED);
        sub.setLayoutX(435);
        sub.setLayoutY(100);


        mySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                myYear.setText("YEAR : " + newValue.intValue());
                if(radioHistogram.isSelected()){
                    drawHisto(Color8, Color1);
                }else if(radioQuadrilater.isSelected()){
                    drawQuadri(Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9);
                }

            }
        });

        radioHistogram.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){

                    myPane.getChildren().remove(sub);
                    root.getChildren().clear();

                    root.getChildren().addAll(rectRed, rectBlue);
                    root.getChildren().addAll(min, max);

                    myPane.getChildren().addAll(sub);

                    setTransparentMeshView(transparent);
                    drawHisto(Color8, Color1);
                }
            }
        });

        radioQuadrilater.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){

                    myPane.getChildren().remove(sub);
                    root.getChildren().clear();

                    root.getChildren().addAll(rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8);
                    root.getChildren().addAll(min, max);

                    myPane.getChildren().addAll(sub);

                    setTransparentCylinder(transparent);

                    drawQuadri(Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9);
                }
            }
        });

        myGraph.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //graphic.getData().clear();
                if(newValue){
                    graphic.getData().clear();
                    graphic.setVisible(true);

                    // Augmenter la taille de la fenetre
                }else{
                    graphic.setVisible(false);
                    // Diminuer la taille de la fenetre
                }
            }
        });

        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double t = Double.parseDouble(speedLecture.getText());
                mySlider.setValue(mySlider.getValue()+(t/10.0));
            }
        };

        ButtonPlay.setOnAction(e -> {
            if(mySlider.getValue()==2020){
                // Si on est à la fin, et que l'on veut directement refaire l'animation
                mySlider.setValue(1880);
                animation.start();
            }else{
                // Sinon
                animation.start();
            }
        });

        ButtonPause.setOnAction(e -> {
            animation.stop();
        });

        ButtonStop.setOnAction(e -> {
            animation.stop();
            mySlider.setValue(1880);
        });

        speedLecture.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                animation.stop();
                if(event.getCode().equals(KeyCode.ENTER)){
                    speedLecture.setText(speedLecture.getText());
                    animation.start();
                }
            }
        });

    }

    private void initQuadri(Group parent, PhongMaterial transparent){
        Point3D topLeft;
        Point3D topRight;
        Point3D bottomLeft;
        Point3D bottomRight;

        for(float lat=-88; lat<=88; lat=lat+8){

            for(float lon=-178; lon<=178; lon=lon+8){

                bottomLeft = geoCoordTo3dCoord(lat-4,lon-4, 1.01f);
                bottomRight = geoCoordTo3dCoord(lat-4,lon+4, 1.01f);
                topLeft = geoCoordTo3dCoord(lat+4,lon-4,1.01f);
                topRight = geoCoordTo3dCoord(lat+4,lon+4, 1.01f);

                MeshView meshView = AddQuadri(parent, topRight,bottomRight, bottomLeft, topLeft, transparent);

                float finalLat = lat;
                float finalLon = lon;

                meshView.setOnMouseClicked(e -> {
                    LatitudeLabel.setText("Latitude : " + finalLat);
                    LongitudeLabel.setText("Longitude : " + finalLon);

                    XYChart.Series series = new XYChart.Series();
                    int j = 1880;
                    for(Float i : data.getEveryAnomaly(finalLat, finalLon)){
                        if(!i.equals(Float.NaN)){
                            series.getData().add(new XYChart.Data(Integer.toString(j),i));
                        }
                        j=j+1;
                    }
                    graphic.getData().add(series);
                });

                laMap.put(new Location_Model(lat,lon),meshView);
            }
        }
    }

    private void initHisto(Group parent, PhongMaterial transparent){

        for(float lat=-88; lat<=88; lat=lat+8){

            for(float lon=-178; lon<=178; lon=lon+8) {
                Float x = data.getValue(lat, lon, Integer.toString((int)mySlider.getValue()));
                Point3D pos = geoCoordTo3dCoord(lat, lon, 1);
                Point3D longueur = geoCoordTo3dCoord(lat, lon, 1.1f);
                Cylinder line = createLine(pos, longueur, transparent);

                histoMap.put(new Location_Model(lat, lon), line);
                parent.getChildren().add(line);

            }
        }
    }

    private void drawHisto(PhongMaterial Blue, PhongMaterial Red){
        Float x;
        for(Location_Model i : histoMap.keySet()){
            x = data.getValue(i.getLatitude(), i.getLongitude(), Integer.toString((int)mySlider.getValue()));

            if(x>=0){
                histoMap.get(i).setMaterial(Red);
                histoMap.get(i).setHeight(Math.round(x*100)/100f);
            }else if(x<0){
                histoMap.get(i).setMaterial(Blue);
                histoMap.get(i).setHeight(Math.round(-x*100)/100f);
            }
        }
    }

    private void setTransparentCylinder(PhongMaterial transparent){
        for(Location_Model i : histoMap.keySet()){
            histoMap.get(i).setMaterial(transparent);
        }
    }

    private void drawQuadri(PhongMaterial Color1, PhongMaterial Color2, PhongMaterial Color3, PhongMaterial Color4, PhongMaterial Color5, PhongMaterial Color6, PhongMaterial Color7, PhongMaterial Color8, PhongMaterial Color9){

        Float value;
        MeshView mesh;

        for(float lat=-88; lat<88; lat=lat+8){

            for(float lon=-178; lon<178; lon=lon+8){

                value = data.getValue(lat, lon, Integer.toString((int)mySlider.getValue()));
                mesh = laMap.get(new Location_Model(lat,lon));

                if(6 <= value){
                    mesh.setMaterial(Color1);
                }else if( (4<=value) && (value<6)){
                    mesh.setMaterial(Color2);
                }else if( (2<=value) && (value<4)){
                    mesh.setMaterial(Color3);
                }else if( (0<=value) && (value<2)){
                    mesh.setMaterial(Color4);
                }else if( (-2<=value) && (value<0)){
                    mesh.setMaterial(Color5);
                }else if((-4<=value) && (value<-2)){
                    mesh.setMaterial(Color6);
                }else if((-6<=value) && (value<-4)){
                    mesh.setMaterial(Color7);
                }else if(value <-6){
                    mesh.setMaterial(Color8);
                }else{
                    mesh.setMaterial(Color9);
                }
            }
        }
    }

    private MeshView AddQuadri(Group parent, Point3D topRight, Point3D bottomRight, Point3D bottomLeft, Point3D topLeft, PhongMaterial transparent){
        final TriangleMesh triangleMesh = new TriangleMesh();

        final float[] points = {
                (float) topRight.getX(), (float) topRight.getY(), (float) topRight.getZ(),
                (float) topLeft.getX(), (float) topLeft.getY(), (float) topLeft.getZ(),
                (float) bottomLeft.getX(), (float) bottomLeft.getY(), (float) bottomLeft.getZ(),
                (float) bottomRight.getX(), (float) bottomRight.getY(), (float) bottomRight.getZ()
        };
        final  float[] textCoords = {
                1, 1,
                1, 0,
                0, 1,
                0, 0
        };

        final int[] faces = {
                0, 1, 1, 0, 2, 2,
                0, 1, 2, 2, 3, 3,
        };

        triangleMesh.getPoints().setAll(points);
        triangleMesh.getTexCoords().setAll(textCoords);
        triangleMesh.getFaces().setAll(faces);

        final MeshView meshView = new MeshView(triangleMesh);
        meshView.setMaterial(transparent);
        parent.getChildren().addAll(meshView);

        return meshView;
    }

    // From Rahel Lüthy : https://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
    public Cylinder createLine(Point3D origin, Point3D target, PhongMaterial Color) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(0.01f, height/2, 2);
        line.setMaterial(Color);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

    private void setTransparentMeshView(PhongMaterial transparent){
        for(Location_Model i : laMap.keySet()){
            laMap.get(i).setMaterial(transparent);
        }
    }

    public static Point3D geoCoordTo3dCoord(float lat, float lon, float rayon) {
        float lat_cor = lat + TEXTURE_LAT_OFFSET;
        float lon_cor = lon + TEXTURE_LON_OFFSET;
        return new Point3D(
                -java.lang.Math.sin(java.lang.Math.toRadians(lon_cor)) * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor)) * rayon,
                -java.lang.Math.sin(java.lang.Math.toRadians(lat_cor)) * rayon,
                java.lang.Math.cos(java.lang.Math.toRadians(lon_cor)) * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor))* rayon) ;
    }



}
