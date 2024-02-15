final String outputFile = "../outputs/2024-02-15--18-01-53/";
Integer totalBlocksWidth = 0;
Integer totalBlocksHeight = 0;
Integer borders = 10;
Integer snapshotIndex = 0;

JSONArray dynamic;
JSONObject data;

Float blockGap = 0.25;
void setup() {
  size(800, 800);
  ellipseMode(CENTER);
  dynamic = loadJSONArray(outputFile + "snapshots.json");
  data = loadJSONObject(outputFile + "static.json");
  JSONObject config = data.getJSONObject("config");
  totalBlocksWidth = config.getInt("totalBlocksWidth");
  totalBlocksHeight = config.getInt("totalBlocksHeight");
  drawBlocks();
}

void drawBlocks() {
    float blockW = ((width - 2 * borders) * 1.0/totalBlocksWidth);
    println(totalBlocksWidth);
    float gapX = blockW * blockGap;
    float blockH = ((height - 2 * borders) * 1.0/totalBlocksHeight);
    float gapY = blockH * blockGap;
    
    //blockH *= (1 - blockGap);
    fill(150);
    noStroke();
    for (int i = 0; i < totalBlocksWidth; i++) {
      for (int j = 0; j < totalBlocksHeight; j++) {
        rect(i*blockW + gapX/2.0 + borders, j*blockH + gapY/2.0 + borders, blockW - gapX, blockH - gapY, gapX/4);
      }
    }
    
}

void drawCar(int road, int pos) {
  float sclL = (width - 2*border) / total
}


void draw() {
  JSONArray snap = dynamic.getJSONObject(snapshotIndex);
  JSONArray ids = snap.getJSONArray("id");
  JSONArray pos = snap.getJSONArray("p");
  JSONArray road = snap.getJSONArray("r");
  for (int i = 0; i < pos.size(); i++) {
    
  }
  noLoop();
}
