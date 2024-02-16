final String outputFile = "../../outputs/2024-02-15--21-46-46/";
Integer totalBlocksWidth = 0;
Integer totalBlocksHeight = 0;
Integer borders = 20;
Integer snapshotIndex = 0;
Integer simSnaps = 0;
Float carLength = 0.0;

JSONArray dynamic;
JSONObject data;
ArrayList<Road> roads;
ArrayList<Node> nodes;
ArrayList<Integer> roadColors;

Float blockGap = 0.05;
void setup() {
  size(1000, 1000);
  ellipseMode(CENTER);
  dynamic = loadJSONArray(outputFile + "snapshots.json");
  data = loadJSONObject(outputFile + "static.json");
  JSONObject config = data.getJSONObject("config");
  JSONArray roadsData = data.getJSONArray("roads");
  JSONArray nodesData = data.getJSONArray("nodes");
  totalBlocksWidth = config.getInt("totalBlocksWidth");
  totalBlocksHeight = config.getInt("totalBlocksHeight");
  carLength = config.getFloat("carLength");
  simSnaps = dynamic.size();
  
  nodes = new ArrayList<Node>();
  roads = new ArrayList<Road>();
  roadColors = new ArrayList<Integer>();
  
  for (int i = 0; i < nodesData.size(); i++) {
    JSONObject node = nodesData.getJSONObject(i);
    int id = i;
    float x = node.getFloat("x");
    float y = node.getFloat("y");
    nodes.add(new Node(id, x, y));
  }
  
  colorMode(HSB);
  for (int i = 0; i < roadsData.size(); i++) {
    JSONObject data = roadsData.getJSONObject(i);
    int startNode = data.getInt("start");
    int endNode = data.getInt("end");
    String dir = data.getString("d");
    roads.add(new Road(i, nodes.get(startNode), nodes.get(endNode), dir));
    roadColors.add(color(random(256), 255, 255));
  }
  colorMode(RGB);
  
  
  
  drawBlocks();
}

void drawBlocks() {
    float blockW = ((width - 2 * borders) * 1.0/totalBlocksWidth);
    float gapX = blockW * blockGap;
    float blockH = ((height - 2 * borders) * 1.0/totalBlocksHeight);
    float gapY = blockH * blockGap;
    
    //blockH *= (1 - blockGap);
    fill(220);
    noStroke();
    for (int i = 0; i < totalBlocksWidth; i++) {
      for (int j = 0; j < totalBlocksHeight; j++) {
        rect(i*blockW + gapX/2.0 + borders, j*blockH + gapY/2.0 + borders, blockW - gapX, blockH - gapY, gapX/4);
    }
  } 
}

void drawRoads() {
    float mapWidth = totalBlocksWidth * 100;
    float mapHeight = totalBlocksHeight * 100;
    float blockW = (width - 2*borders) / totalBlocksWidth;
    float sclL = (carLength / 100.0) * blockW;
    strokeWeight(sclL/2.2);
    stroke(230);
    for (Road r : roads) {
      Node n1 = r.start;
      Node n2 = r.end;
      float n1x = map(n1.x, 0, mapWidth, borders, width-borders);
      float n2x = map(n2.x, 0, mapWidth, borders, width-borders);
      float n1y = map(n1.y, 0, mapHeight, borders, height-borders);
      float n2y = map(n2.y, 0, mapHeight, borders, height-borders);
      line(n1x, n1y, n2x, n2y);
    }
}

void drawCar(Road road, float pos, int stop) {
  float blockW = (width - 2*borders) / totalBlocksWidth;
  float sclL = (carLength / 100.0) * blockW;
  String dir = road.dir;
  Node n1 = road.start;
  Node n2 = road.end;
  float x = 0;
  float y = 0;
  float wMult = 1;
  float hMult = 1;
  if (stop > 0) {
    fill(51, 51, 51);
  } else {
    fill(0, 0, 255);
  }
  noStroke();
  if (dir.equals("N") || dir.equals("S")) {
    x = n1.x;
    y = map(pos, 0, 100.0, n1.y, n2.y);
  } else if (dir.equals("W") || dir.equals("E")) {
    x = map(pos, 0, 100.0, n1.x, n2.x);
    y = n1.y;
  } else {
    throw new RuntimeException("Invalid dir: " + dir);
  }
  float screenX = map(x, 0, 100.0 * totalBlocksWidth, borders, width-borders);
  float screenY = map(y, 0, 100.0 * totalBlocksHeight, borders, height-borders);
  float screenCarW = sclL;
  float screenCarH = sclL;
  if (dir.equals("N")) {
    screenX -= sclL/4;
    screenY -= sclL/2;
    screenCarW /= 2;
  } else if (dir.equals("S")) {
    screenX -= sclL/4;
    screenY -= sclL/2;
    screenCarW /= 2;
  } else if (dir.equals("W") || dir.equals("E")) {
    screenX -= sclL/2;
    screenY -= sclL/4;
    screenCarH /= 2;
  }
  rect(screenX, screenY, screenCarW, screenCarH);
}


void draw() {
  background(255);
  drawBlocks();
  drawRoads();
  JSONObject snap = dynamic.getJSONObject(snapshotIndex);
  JSONArray ids = snap.getJSONArray("id");
  JSONArray pos = snap.getJSONArray("p");
  JSONArray road = snap.getJSONArray("r");
  JSONArray stopped = snap.getJSONArray("s");
  for (int i = 0; i < pos.size(); i++) {
    float posI = pos.getFloat(i);
    int roadID = road.getInt(i);
    int s = stopped.getInt(i);
    Road r = roads.get(roadID);
    drawCar(r, posI, s);
  }
  if (frameCount > 0 && frameCount % 1 == 0 && snapshotIndex < simSnaps) {
    snapshotIndex += 1;
    println(snapshotIndex);
  } 
  if (snapshotIndex >= simSnaps) {
    snapshotIndex = 0;
  }
  //if (frameCount < 20 * 60) {
  //  saveFrame("data/#####.png");
  //}
  
}
