using System.Collections.Generic;
using System.Text;

namespace SVGTool
{
    public class Circle : StyleManager, IShape
    {
        public Point Position { get; set; }
        public float Radius { get; set; }

        /// <summary>
        /// Creates a Circle.
        /// </summary>
        /// <param name="x">X Position</param>
        /// <param name="y">Y Position</param>
        /// <param name="r">Radius</param>
        public Circle(float x, float y, float r)
        {
            Position = new Point(x, y);
            Radius = r;
            StyleElements = new HashSet<IStyleElement>();
        }
        public string ToSVGString()
        {
            StringBuilder exportString = new StringBuilder($"<circle cx=\"{Position.X}\" cy=\"{Position.Y}\" r=\"{Radius}\" ");
            ApplyStyleElements(exportString, StyleElements);
            exportString.Append(" />");
            return exportString.ToString();
        }
    }
}
