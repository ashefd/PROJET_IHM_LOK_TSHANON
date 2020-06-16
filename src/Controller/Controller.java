package Controller;

import Model.Data_Model;
import Model.Location_Model;
import com.interactivemesh.jfx.importer.ImportException;
import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;


import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;

public class Controller{

    Data_Model data;

    private static final float TEXTURE_LAT_OFFSET = -0.2f;
    private static final float TEXTURE_LON_OFFSET = 2.8f;

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
    public void initialize(){
        data = new Data_Model("resources/tempanomaly_4x4grid.csv");

        mySlider.setMin(1880);
        mySlider.setMax(2020);
        mySlider.setValue(1880);

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
        SubScene scene = new SubScene(root3D, 390, 350, true, SceneAntialiasing.BALANCED );
        scene.setCamera(camera);
        scene.setFill(Color.gray(0.12));
        myPane.getChildren().addAll(scene);

        mySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                myYear.setText("YEAR : " + newValue.intValue());
                if(radioHistogram.isSelected()){
                    drawAnomalyHisto(earth);
                }else if(radioQuadrilater.isSelected()){
                    drawAnomalyQuadri(earth);
                }
            }
        });

        radioHistogram.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    drawAnomalyHisto(earth);
                }
            }
        });

        radioQuadrilater.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){

                    drawAnomalyQuadri(earth);
                }
            }
        });

    }

    public void drawAnomalyHisto(Group parent){
        Point3D pos;
        Point3D longueur;
        for(Location_Model i : data.getKnowZone()){
            pos = geoCoordTo3dCoord(i.getLatitude(), i.getLongitude(), 1);
            longueur = geoCoordTo3dCoord(i.getLatitude(), i.getLongitude(), 1+data.getValue(i.getLatitude(), i.getLongitude(), Integer.toString((int)mySlider.getValue())));
            parent.getChildren().add(createLine(pos, longueur));
        }
    }

    // TODO modify the color according to the anomaly value
    private void drawAnomalyQuadri(Group parent){

        //Create the color
        final PhongMaterial red = new PhongMaterial();
        Color redx = new Color(0,0,0.3, 0.1);
        red.setDiffuseColor(redx);
        red.setSpecularColor(redx);

        final PhongMaterial blue = new PhongMaterial();
        Color bluex = new Color(0,0,0.3, 0.1);
        blue.setDiffuseColor(bluex);
        blue.setSpecularColor(bluex);


        Point3D topLeft;
        Point3D topRight;
        Point3D bottomLeft;
        Point3D bottomRight;

        for(int lat=-90; lat<90; lat=lat+10){

            for(int lon=-180; lon<180; lon=lon+10){

                bottomLeft = geoCoordTo3dCoord(lat-5,lon-5, 1.08f);
                bottomRight = geoCoordTo3dCoord(lat-5,lon+5, 1.08f);
                topLeft = geoCoordTo3dCoord(lat+5,lon-5,1.08f);
                topRight = geoCoordTo3dCoord(lat+5,lon+5, 1.08f);

                // adapter selon la temperature de la zone
                AddQuadrilateral(parent,topRight,bottomRight, bottomLeft, topLeft, red);


            }
        }
    }

    public void displayTown(Group parent, String name, float latitude, float longitude){
        Sphere sphere = new Sphere(0.01);
        sphere.setId(name);
        Point3D location = geoCoordTo3dCoord(latitude,longitude, 1);
        sphere.setTranslateX(location.getX());
        sphere.setTranslateY(location.getY());
        sphere.setTranslateZ(location.getZ());
        parent.getChildren().add(sphere);
    }



    // From Rahel Lüthy : https://netzwerg.ch/blog/2015/03/22/javafx-3d-line/
    public Cylinder createLine(Point3D origin, Point3D target) {
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(0.01f, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }

    public static Point3D geoCoordTo3dCoord(float lat, float lon, float rayon) {
        float lat_cor = lat + TEXTURE_LAT_OFFSET;
        float lon_cor = lon + TEXTURE_LON_OFFSET;
        return new Point3D(
                -java.lang.Math.sin(java.lang.Math.toRadians(lon_cor)) * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor)) * rayon,
                -java.lang.Math.sin(java.lang.Math.toRadians(lat_cor)) * rayon,
                java.lang.Math.cos(java.lang.Math.toRadians(lon_cor)) * java.lang.Math.cos(java.lang.Math.toRadians(lat_cor))* rayon) ;
    }

    private void AddQuadrilateral(Group parent, Point3D topRight, Point3D bottomRight, Point3D bottomLeft, Point3D topLeft, PhongMaterial material){
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
        meshView.setMaterial(material);
        parent.getChildren().addAll(meshView);
    }

}
