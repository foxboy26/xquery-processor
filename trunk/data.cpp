#include <iostream>
#include <fstream>
#include <cstdlib>
using namespace std;

int main(int argc, char* argv[])
{
  int user_num = atoi(argv[1]);
  int order_num = atoi(argv[2]);
  int prod_num = atoi(argv[3]);

  ofstream fout("user.xml");

  fout << "<user>" << endl;
  for (int i = 0; i < user_num; i++)
  {
    fout << "<tuple>" << endl;
    fout << "<id>" << i << "</id>" << endl;
    fout << "<age>" << rand() % 2 + 1 << "</age>" << endl;
    fout << "</tuple>" << endl;
  }
  fout << "</user>" << endl;

  fout.close();

  fout.open("order.xml");
  fout << "<order>" << endl;
  for (int i = 0; i < order_num; i++)
  {
    fout << "<tuple>" << endl;
    fout << "<userid>" << rand() % user_num << "</userid>" << endl;
    fout << "<product>" << rand() % prod_num << "</product>" << endl;
    fout << "</tuple>" << endl;
  }
  fout << "</order>" << endl;
  fout.close();

  fout.open("product.xml");
  fout << "<product>" << endl;
  for (int i = 0; i < order_num; i++)
  {
    fout << "<tuple>" << endl;
    fout << "<id>" << i << "</id>" << endl;
    fout << "<name>" << "product" << i << "</name>" << endl;
    fout << "</tuple>" << endl;
  }
  fout << "</product>" << endl;
  fout.close();

  return 0;
}
