using System.Collections.Generic;
using System.Text;

namespace SVGTool
{
    public class Polyline : StyleManager, IShape
    {
        public List<Point> Points { get; }
        public Polyline()
        {
            Points = new List<Point>();
            StyleElements = new HashSet<IStyleElement>();
        }
        public Polyline(List<Point> points)
        {
            Points = points;
            StyleElements = new HashSet<IStyleElement>();
        }
        public string ToSVGString()
        {
            StringBuilder exportString = new StringBuilder("<polyline points=\"");
            Points.ForEach(delegate (Point point)
            {
                exportString.Append(point.ToSVGStyleString() + " ");
            });
            FinalizePointList(exportString);
            ApplyStyleElements(exportString, StyleElements);
            exportString.Append(" />");
            return exportString.ToString();
        }

        private static void FinalizePointList(StringBuilder exportString)
        {
            exportString.Replace(" ", "\"", exportString.Length - 1, 1);
        }
    }
}
