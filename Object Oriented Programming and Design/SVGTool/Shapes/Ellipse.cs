using System.Collections.Generic;
using System.Text;

namespace SVGTool
{
    public class Ellipse : StyleManager, IShape
    {
        public Point Position { get; set; }
        public float Rx { get; set; }
        public float Ry { get; set; }

        /// <summary>
        /// Creates an Ellipse, stretchable on the X and Y axes.
        /// </summary>
        /// <param name="x">X Position</param>
        /// <param name="y">Y Position</param>
        /// <param name="rx">Radius on X axis</param>
        /// <param name="ry">Radius on Y axis</param>
        public Ellipse(float x, float y, float rx, float ry)
        {
            Position = new Point(x, y);
            Rx = rx;
            Ry = ry;
            StyleElements = new HashSet<IStyleElement>();
        }
        public string ToSVGString()
        {
            StringBuilder exportString = new StringBuilder($"<ellipse cx=\"{Position.X}\" cy=\"{Position.Y}\" rx=\"{Rx}\" ry=\"{Ry}\" ");
            ApplyStyleElements(exportString, StyleElements);
            exportString.Append(" />");
            return exportString.ToString();
        }
    }
}
