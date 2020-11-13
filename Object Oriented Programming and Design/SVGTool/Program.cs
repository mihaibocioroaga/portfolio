using System;
using System.Collections.Generic;
using System.IO;

namespace SVGTool
{
    class Program
    {

        /* 
         * Assignment created by Mihai Bocioroaga
         * - Student no. 18413532 -
         * 
         * This solution focuses on modularity, allowing new shapes and styles to be introduced as needed.
         * Below is a simple demo of random shapes scattered about.
         * 
         * Ideally I would have finished my ShapeFactory class, which would allow easy development of a CLI,
         * but for the purpose of hard-coding the shapes within Main it is not necessary.
         */
        static void Main(string[] args)
        {
            Canvas canvas = new Canvas(400, 400);

            Circle circle = new Circle(40, 40, 25);
            circle.AddStyleElement(new Fill(255, 0, 0));
            canvas.Add(circle);

            Rectangle rect = new Rectangle(20, 5, 40, 20);
            rect.AddStyleElement(new Fill(100, 100, 25));
            canvas.Add(rect);

            List<Point> points = new List<Point>
            {
                new Point(5, 24),
                new Point(8, 11),
                new Point(22, 15),
                new Point(7, 4)
            };
            Polyline polyline = new Polyline(points);
            polyline.AddStyleElement(new Stroke(20,215,60));
            polyline.AddStyleElement(new StrokeWidth(3));
            canvas.Add(polyline);

            using (StreamWriter sw = new StreamWriter("output.svg"))
                sw.WriteLine(canvas.Export());

            Console.WriteLine("Canvas exported to root directory of program.");
        }
    }
}