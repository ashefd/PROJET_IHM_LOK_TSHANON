package Controller;

import Model.Data_Model;
import Model.Location_Model;
import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.junit.platform.engine.support.hierarchical.OpenTest4JAwareThrowableCollector;


import java.net.URL;
import java.util.HashMap;
import java.util.function.UnaryOperator;


public class Controller {

    private Data_Model data;

    private HashMap<Location_Model, MeshView> laMap;
    private HashMap<Location_Model, Cylinder> histoMap;

    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;

    private UnaryOperator<TextFormatter.Change> filter;

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
    private Button myGraph;

    @FXML
    private LineChart graphic;

    @FXML
    public void initialize(){

        //Getting the data
        data = new Data_Model("src/resources/tempanomaly_4x4grid.csv");

        // Initializing the view
        mySlider.setMin(1880);
        mySlider.setMax(2020);
        mySlider.setValue(1880);
        myYear.setText("YEAR : 1880");
        graphic.setCreateSymbols(false);
        speedLecture.setText("1");

        // Creating the Hashmap
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
        SubScene scene = new SubScene(root3D, 555, 349, true, SceneAntialiasing.BALANCED );
        scene.setCamera(camera);
        scene.setFill(Color.gray(0.12));
        myPane.getChildren().addAll(scene);


        // Creating the Color and associating it to a rectangle for the caption
        double place = 180;
        Color color1 = new Color(1,0,0, 0.4);
        final PhongMaterial Color1 = new PhongMaterial();
        Color1.setDiffuseColor(color1);
        Color1.setSpecularColor(color1);
        Rectangle rect1 = new Rectangle(place + 140, 5, 20, 20);
        rect1.setFill(color1);
        Rectangle rectRed = new Rectangle(place+85,5,70, 20);
        rectRed.setFill(color1);

        Color color2 = new Color(1,0.3,0, 0.4);
        final PhongMaterial Color2 = new PhongMaterial();
        Color2.setDiffuseColor(color2);
        Color2.setSpecularColor(color2);
        Rectangle rect2 = new Rectangle(place+ 120, 5, 20, 20);
        rect2.setFill(color2);

        Color color3 = new Color(1,0.6,0, 0.4);
        final PhongMaterial Color3 = new PhongMaterial();
        Color3.setDiffuseColor(color3);
        Color3.setSpecularColor(color3);
        Rectangle rect3 = new Rectangle(place+ 100, 5, 20, 20);
        rect3.setFill(color3);

        Color color4 = new Color(1,0.8,0, 0.4);
        final PhongMaterial Color4 = new PhongMaterial();
        Color4.setDiffuseColor(color4);
        Color4.setSpecularColor(color4);
        Rectangle rect4 = new Rectangle(place+80, 5, 20, 20);
        rect4.setFill(color4);

        Color color5 = new Color(0.3,0.9,1, 0.4);
        final PhongMaterial Color5 = new PhongMaterial();
        Color5.setDiffuseColor(color5);
        Color5.setSpecularColor(color5);
        Rectangle rect5 = new Rectangle(place+ 60, 5, 20, 20);
        rect5.setFill(color5);

        Color color6 = new Color(0,0.6,1, 0.4);
        final PhongMaterial Color6 = new PhongMaterial();
        Color6.setDiffuseColor(color6);
        Color6.setSpecularColor(color6);
        Rectangle rect6 = new Rectangle(place+40, 5, 20, 20);
        rect6.setFill(color6);

        Color color7 = new Color(0,0.3,1, 0.4);
        final PhongMaterial Color7 = new PhongMaterial();
        Color7.setDiffuseColor(color7);
        Color7.setSpecularColor(color7);
        Rectangle rect7 = new Rectangle(place+20, 5, 20, 20);
        rect7.setFill(color7);

        Color color8 = new Color(0,0,1, 0.4);
        final PhongMaterial Color8 = new PhongMaterial();
        Color8.setDiffuseColor(color8);
        Color8.setSpecularColor(color8);
        Rectangle rect8 = new Rectangle(place, 5, 20, 20);
        rect8.setFill(color8);

        Rectangle rectBlue = new Rectangle(place, 5, 70, 20);
        rectBlue.setFill(color8);

        Color color9 = new Color(0.1,0.1,0.1, 0.7);
        final PhongMaterial Color9 = new PhongMaterial();
        Color9.setDiffuseColor(color9);
        Color9.setSpecularColor(color9);

        PhongMaterial transparent = new PhongMaterial();
        transparent.setSpecularColor(Color.TRANSPARENT);
        transparent.setDiffuseColor(Color.TRANSPARENT);

        // Initialization of the Quadrilateral and the histogram
        initQuadri(earth, transparent);
        initHisto(earth, transparent);

        // Caption + min + max
        Group root = new Group();

            // Explain
        Label text = new Label();
        text.setText("Temperature - ");
        text.setTextFill(Color.WHITE);

            // Min
        Label min = new Label();
        min.setText("Minimum : " + Float.toString(data.getMin()));
        min.setTextFill(Color.WHITE);
        min.setTranslateY(10);

            // Max
        Label max = new Label();
        max.setText("Maximum : " + Float.toString(data.getMax()));
        max.setTextFill(Color.WHITE);
        max.setTranslateY(20);

            // Creating the subscene
        SubScene sub = new SubScene(root, 700, 100, true, SceneAntialiasing.BALANCED);
        sub.setLayoutX(5);
        sub.setLayoutY(310);
        root.getChildren().addAll(text, min, max);
        myPane.getChildren().addAll(sub);


        // adding Listener which change the data according the the slider's value
        mySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                // everytime slider's value is changing, representation of anomaly will change too

                    //Changing the text
                myYear.setText("YEAR : " + newValue.intValue());
                if(radioHistogram.isSelected()){
                    //Changing the histogram representation
                    drawHisto(Color8, Color1);
                }else if(radioQuadrilater.isSelected()){
                    //Changing the quadrilateral representation
                    drawQuadri(Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9);
                }

            }
        });

        // Adding a Listener to the radio button Histogram so as to do the representation with Cylinder
        radioHistogram.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    // Making sure that we don't put again the subscene
                    myPane.getChildren().remove(sub);
                    root.getChildren().clear();

                    // Adding to the root for the caption
                    root.getChildren().addAll(rectRed, rectBlue);
                    root.getChildren().addAll(text, min, max);

                    // Adding to the pane
                    myPane.getChildren().addAll(sub);

                    // Setting the quadrilateral as transparent
                    setTransparentMeshView(transparent);

                    // Drawing the histogram
                    drawHisto(Color8, Color1);
                }
            }
        });

        // Adding a Listener to the radio button Quadrilateral so as to do the representation with quadrilateral
        radioQuadrilater.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if(newValue){
                    // Making sure that we don't put again the subscene
                    myPane.getChildren().remove(sub);
                    root.getChildren().clear();

                    // Adding to the root for the caption
                    root.getChildren().addAll(rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8);
                    root.getChildren().addAll(text, min, max);

                    // Adding to the pane
                    myPane.getChildren().addAll(sub);

                    // Setting the cylinders as transparent
                    setTransparentCylinder(transparent);

                    // Drawing the quadrilateral with the right color
                    drawQuadri(Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9);
                }
            }
        });

        // Adding a Listener to the myGraph button so as to clear the graphic
        myGraph.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                graphic.getData().clear();
            }
        });

        // Creating an Animation Timer so as to do the animation
        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double t = Double.parseDouble(speedLecture.getText());
                mySlider.setValue(mySlider.getValue()+(t/60.0));
            }
        };


        // Adding a Listener for the animation's buttons
        ButtonPlay.setOnAction(e -> {
            if(mySlider.getValue()==2020){
                // If this is the end of the animation but we want to watch it again
                mySlider.setValue(1880);
                animation.start();
            }else{
                // Making sure that the value is correct
                if (!speedLecture.getText().matches("^[-+]?[0-9]*[.]?[0-9]*([eE]?[-+]*[0-9]*)?$")){
                    // If the value is not correct, set the animation's speed at 1
                    speedLecture.setText("1");
                }
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


        // Adding a listener to the speedLecture TextField.
        speedLecture.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                animation.stop();
                // Making sure that the value is correct
                if (speedLecture.getText().matches("^[-+]?[0-9]*[.]?[0-9]*([eE]?[-+]*[0-9]*)?$") && event.getCode().equals(KeyCode.ENTER)){
                    if(speedLecture.getText().endsWith("e")){  // If the speed value ends with "e", then add 1 at the end of the value
                        speedLecture.setText(speedLecture.getText()+"1");
                    }
                    // If it is the end of the animation but we want to watch it again
                    if(mySlider.getValue() == 2020){
                        mySlider.setValue(1880);
                    }
                    speedLecture.setText(speedLecture.getText());
                    animation.start();
                }
            }
        });





    }




    // Creating every Quadrilateral on earth but set transparent
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

                // Adding an EventHandler which give the latitude and the longitude of the zone
                // + Adding to the data of the zone to the graphic
                meshView.setOnMouseClicked(e -> {
                    LatitudeLabel.setText("Latitude : " + finalLat);
                    LongitudeLabel.setText("Longitude : " + finalLon);

                    XYChart.Series series = new XYChart.Series();
                    int j = 1880;

                    // Getting the data of the zone to the graphic
                    for(Float i : data.getEveryAnomaly(finalLat, finalLon)){
                        if(!i.equals(Float.NaN)){
                            XYChart.Data<String, Float> Data = new XYChart.Data(Integer.toString(j),i);
                            Data.setNode(new HoverAndClicked(parent, Integer.toString(j), i, finalLat, finalLon));
                            series.getData().add(Data);
                            series.setName("Latitude : " + finalLat + " , longitude : " + finalLon);
                        }
                        j=j+1;
                    }

                    graphic.getData().add(series);

                });

                laMap.put(new Location_Model(lat,lon),meshView);
            }
        }
    }

    // Creating every Cylinder on earth but set transparent
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

    // Drawing every Cylinder according to the temperature
    private void drawHisto(PhongMaterial Blue, PhongMaterial Red){
        Float x;

        for(Location_Model i : histoMap.keySet()){
            x = data.getValue(i.getLatitude(), i.getLongitude(), Integer.toString((int)mySlider.getValue()));
            if(x>=0){
                // Making every positive anomaly value blue
                histoMap.get(i).setMaterial(Red);
                histoMap.get(i).setHeight(Math.round(x*100)/100f);
            }else if(x<0){
                // Making every negative anomaly value red
                histoMap.get(i).setMaterial(Blue);
                histoMap.get(i).setHeight(Math.round(-x*100)/100f);
            }
        }
    }

    // Making the Cylinder transparent
    private void setTransparentCylinder(PhongMaterial transparent){
        for(Location_Model i : histoMap.keySet()){
            histoMap.get(i).setMaterial(transparent);
        }
    }

    // Drawing every quadrilateral according to the temperature
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

    // Making the MeshView transparent
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

    public void addLocation(Group parent, float latitude, float longitude){
        Sphere sphere = new Sphere(0.1);

        // Setting the Id of the sphere to make sure that the sphere is unique
        sphere.setId(Float.toString(latitude)+"-"+Float.toString(longitude));

        Point3D location = geoCoordTo3dCoord(latitude,longitude, 1);
        sphere.setTranslateX(location.getX());
        sphere.setTranslateY(location.getY());
        sphere.setTranslateZ(location.getZ());

        // Drawing the sphere on earth
        parent.getChildren().add(sphere);

        // Adding an EventHandler. The sphere disapear whenever it is clicked on
        sphere.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                parent.getChildren().remove(sphere);
            }
        });
    }



    // Internal class
    public class HoverAndClicked extends StackPane {

        public HoverAndClicked(Group parent, String string, Object object, float lat, float lon){

            final Label label = new Label(object + "");

            // Show the value of the anomaly
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.getStyleClass().addAll("chart-line-symbol");
                    label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
                    getChildren().setAll(label);
                    toFront();
                }
            });

            // Clear it
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    getChildren().clear();
                }
            });

            // Set the year from the value selected
            setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mySlider.setValue(Integer.parseInt(string));
                    boolean inside = false;
                    for(Node node : parent.getChildren()) {  // Making sure that only one sphere will be drawn
                        if(node.getId() == null){
                            // Do nothing
                        }else if(node.getId().equals(Float.toString(lat)+"-"+Float.toString(lon))){
                            inside = true;
                        }
                    }
                    if(!inside){
                        addLocation(parent, lat, lon);
                    }
                }
            });
        }
    }


}
