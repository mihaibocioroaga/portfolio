using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SVGTool
{
    static class ShapeFactory
    {
        //TODO No support for Polyline and style addition
        public static IShape GetShape(string shapeName, string attributes, string style)
        {
            float[] att = ParseAttributes(attributes);
            return shapeName switch
            {
                "circle" => new Circle(att[0], att[1], att[2]),
                "rect" => new Rectangle(att[0], att[1], att[2], att[3]),
                "ellipse" => new Ellipse(att[0], att[1], att[2], att[3]),
                "line" => new Line(att[0], att[1], att[2], att[3]),
                _ => throw new ArgumentException($"Invalid attributes or shape name provided to shape factory for shape '{shapeName}'"),
            };
        }

        private static float[] ParseAttributes(string attributes)
        {
            string[] Attributes;
            float[] ParsedAttributes;
            Attributes = attributes.Split(" ");
            ParsedAttributes = new float[Attributes.Length];
            for (int i = 0; i < Attributes.Length; i++)
            {
                ParsedAttributes[i] = float.Parse(Attributes[i]);
            }
            return ParsedAttributes;
        }
    }
}
