final String outputFile = "../outputs/output_20240208_193923.txt";
Integer totalBlocksWidth = 0;
Integer totalBlocksHeight = 0;

Float blockGap = 0.1;
void setup() {
  size(600, 600);
  ellipseMode(CENTER);
  JSONObject data = loadJSONObject(outputFile);
  JSONObject config = data.getJSONObject("config");
  totalBlocksWidth = config.getInt("totalBlocksWidth");
  totalBlocksHeight = config.getInt("totalBlocksHeight");
  drawBlocks();
}

void drawBlocks() {
    float blockW = (width * 1.0/totalBlocksWidth);
    println(totalBlocksWidth);
    float gapX = blockW * blockGap;
    float blockH = (height * 1.0/totalBlocksHeight);
    float gapY = blockH * blockGap;
    
    //blockH *= (1 - blockGap);
    fill(51, 51, 51);
    noStroke();
    for (int i = 0; i < totalBlocksWidth; i++) {
      for (int j = 0; j < totalBlocksHeight; j++) {
        rect(i*blockW + gapX/2.0, j*blockH + gapY/2.0, blockW - gapX, blockH - gapY, gapX);
      }
    }
    
}

void drawArrows() {
   for (int i = 0; i <= totalBlocksWidth; i++) {
     for (int j = 0; j <= totalBlocksHeight; j++) {
       
     }
   }
}

void drawArrow(float x, float y, color arrowColor, float arrowLength, int direction) {
  float arrowHeadSize = 10; // Size of the arrowhead
  
  // Set the color for the arrow
  stroke(arrowColor);
  strokeWeight(2);
  fill(arrowColor);
  
  // Draw the arrow body based on the direction
  switch (direction) {
    case LEFT:
      line(x, y, x - arrowLength, y);
      break;
    case RIGHT:
      line(x, y, x + arrowLength, y);
      break;
    case UP:
      line(x, y, x, y - arrowLength);
      break;
    case DOWN:
      line(x, y, x, y + arrowLength);
      break;
    default:
      break;
  }
  
  // Draw the arrowhead
  switch (direction) {
    case LEFT:
      ellipse(x - arrowLength, y, arrowHeadSize, arrowHeadSize);
      break;
    case RIGHT:
      ellipse(x + arrowLength, y, arrowHeadSize, arrowHeadSize);
      break;
    case UP:
      ellipse(x, y - arrowLength, arrowHeadSize, arrowHeadSize);
      break;
    case DOWN:
      ellipse(x, y + arrowLength, arrowHeadSize, arrowHeadSize);
      break;
    default:
      return;
  }
  
}
