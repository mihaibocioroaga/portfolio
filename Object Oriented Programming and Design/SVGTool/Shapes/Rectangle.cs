using System.Collections.Generic;
using System.Text;

namespace SVGTool
{
    public class Rectangle : StyleManager, IShape
    {
        public Point Position { get; set; }
        public float Width { get; set; }
        public float Height { get; set; }

        /// <summary>
        /// Creates a Rectangle.
        /// </summary>
        /// <param name="x">X Position</param>
        /// <param name="y">Y Position</param>
        /// <param name="width">Width of the rectangle.</param>
        /// <param name="height">Height of the rectangle.</param>
        public Rectangle(float x, float y, float width, float height)
        {
            Position = new Point(x, y);
            Width = width;
            Height = height;
            StyleElements = new HashSet<IStyleElement>();
        }
        public string ToSVGString()
        {
            StringBuilder exportString = new StringBuilder($"<rect x=\"{Position.X}\" y=\"{Position.Y}\" width=\"{Width}\" height=\"{Height}\" ");
            ApplyStyleElements(exportString, StyleElements);
            exportString.Append(" />");
            return exportString.ToString();
        }
    }
}
