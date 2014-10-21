
#include <iostream>

using namespace std;

class shape
{
  public:
        void setlength(float len){
          length=len;
      }
      void setbreadth(float bre){
          breadth=bre;
      }
      float returnlen()
      {
          return length;
      }
      float returnbre()
      {
          return breadth;
      }
      shape operator+(const shape&);
private:
  float length;
  float breadth;

};


shape shape::operator+(const shape& b) {
   shape shape1;
   shape1.length = this->length + b.length;
   shape1.breadth = this->breadth + b.length;
   return shape1;
}


void shapeOperatorSample() {
  shape shap1, shap2, shap3;

  shap1.setlength(10.0);
  shap1.setbreadth(10.0);
  shap2.setlength(10.0);
  shap2.setbreadth(10.0);
  shap3 = shap1 + shap2;

  cout << "\n The length of the third object is : " << shap3.returnlen();
  cout << "\n The breadth of the third object is : " << shap3.returnbre();
}
